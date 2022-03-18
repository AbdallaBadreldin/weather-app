package eg.iti.weatherapp.main.ui.location

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.PickLocationFragmentBinding
import eg.iti.weatherapp.main.utils.ManagePermissions

class PickLocationFragment : Fragment() {

    private var _binding: PickLocationFragmentBinding? = null
    private val binding get() =_binding!!
    private lateinit var viewModel: PickLocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var sharedPreferences: SharedPreferences
    companion object {
        fun newInstance() = PickLocationFragment()
    }

    lateinit var btnGps:Button
    lateinit var btnMap:Button
    lateinit var btnCity:Button

    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //shared preferences
//         sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_file),Context.MODE_PRIVATE)
       sharedPreferences= PreferenceManager.getDefaultSharedPreferences(requireContext())
        viewModel = ViewModelProvider(this).get(PickLocationViewModel::class.java)
        // TODO: Use the ViewModel

        _binding = PickLocationFragmentBinding.inflate(inflater,container,false)
        val root = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity().application)

        btnGps = binding.gpsLocation
        btnMap = binding.mapLocation
        btnCity = binding.cityLocation


        // Initialize a list of required permissions to request runtime
        val list = listOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(requireActivity(),list,PermissionsRequestCode)


        btnGps.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                managePermissions.checkPermissions()

            getLastKnownLocation()
        }

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        requestLocationPermission()

//        checkWetherPermissionGrantedOrNot()

    }

    private fun getLastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
//                 Got last known location. In some rare situations this can be null.
            //here I am going to save shared pereferences and navigate to next screen where api retrofit :)
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()

            editor.putString(getString(R.string.preference_longitude), location?.longitude.toString())
            editor.putString(getString(R.string.preference_alatitude), location?.altitude.toString())
            editor.apply()
            editor.commit()
            findNavController().navigate(ActionOnlyNavDirections(R.id.action_pick_to_home) ) //for test purposal only
        }
    }

    private fun checkWetherPermissionGrantedOrNot() {

        val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions -> when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.

                Toast.makeText(context,"ACCESS_FINE_LOCATION",Toast.LENGTH_LONG).show()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                Toast.makeText(context,"ACCESS_COARSE_LOCATION",Toast.LENGTH_LONG).show()
            } else -> {
                // No location access granted.
                Toast.makeText(context,"No location",Toast.LENGTH_LONG).show()
            }
        }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun requestLocationPermission() = registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isGranted: Boolean -> if (isGranted) {
        getLastKnownLocation()

    } else {


        // Explain to the user that the feature is unavailable because the
        // features requires a permission that the user has denied. At the
        // same time, respect the user's decision. Don't link to system
        // settings in an effort to convince the user to change their
        // decision.
    }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode -> {
                val isPermissionsGranted = managePermissions
                    .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    // Do the task now
                    Toast.makeText(context, "\"Permissions granted.\"", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "\"Permissions denied.\"", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }




    /* override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
         when (requestCode) {
           requestCode  -> {
                 // If request is cancelled, the result arrays are empty.
                 if ((grantResults.isNotEmpty() &&
                             grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                     // Permission is granted. Continue the action or workflow
                     // in your app.

                 } else {
                     // Explain to the user that the feature is unavailable because
                     // the features requires a permission that the user has denied.
                     // At the same time, respect the user's decision. Don't link to
                     // system settings in an effort to convince the user to change
                     // their decision.
                 }
                 return
             }

             // Add other 'when' lines to check for other
             // permissions this app might request.
             else -> {
                 // Ignore all other requests.
             }
         }
     }*/


}
// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}