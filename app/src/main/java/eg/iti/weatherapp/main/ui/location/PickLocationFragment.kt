package eg.iti.weatherapp.main.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.location.*
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.PickLocationFragmentBinding
import eg.iti.weatherapp.main.utils.ManagePermissions

class PickLocationFragment : Fragment() {

    private var _binding: PickLocationFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PickLocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var sharedPreferences: SharedPreferences
    lateinit var mLocationRequest: LocationRequest

    companion object {
        fun newInstance() = PickLocationFragment()
    }

    lateinit var btnGps: Button
    lateinit var btnMap: Button
    lateinit var btnCity: Button
    lateinit var long: String
    lateinit var lati: String
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        viewModel = ViewModelProvider(this).get(PickLocationViewModel::class.java)
        // TODO: Use the ViewModel seems to use it for shared prefrences with location data getter and setter but this will be latter

        _binding = PickLocationFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity().application)

        btnGps = binding.gpsLocation
        btnMap = binding.mapLocation
        btnCity = binding.cityLocation

        var long: String
        var lat: String
        // Initialize a list of required permissions to request runtime
        val list = listOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(requireActivity(), list, PermissionsRequestCode)


        btnGps.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                managePermissions.checkPermissions()

            getLastKnownLocation()
        }

        btnMap.setOnClickListener {
            findNavController().navigate(ActionOnlyNavDirections(R.id.navigation_map)) //for test purposal only
        }

        btnCity.setOnClickListener {
            context?.toast(getText(R.string.feature_not_available) as String)
        }

        return root
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
//       var loco= fusedLocationClient.getCurrentLocation()
        fusedLocationClient.flushLocations()
        if (isLocationEnabled()) {
            requestNewLocationData()
            fusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()!!
            )




            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//                 Got last known location. In some rare situations this can be null.
                //here I am going to save shared Preference and navigate to next screen where api retrofit :)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()

                editor.putString(
                    getString(R.string.preference_longitude),
                    location?.longitude.toString()
                )
                editor.putString(
                    getString(R.string.preference_alatitude),
                    location?.latitude.toString()
                )
                editor.apply()
                editor.commit()
                findNavController().navigate(ActionOnlyNavDirections(R.id.action_pick_to_home)) //for test purposal only
            }
        } else
            context?.toast(getString(R.string.enable_gps))
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var location: Location = locationResult.lastLocation
            lati = location.latitude.toString()
            long = location.longitude.toString()
//            editor.commit()
//            findNavController().navigate(ActionOnlyNavDirections(R.id.action_pick_to_home) ) //for test purposal only
//            findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
//            findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun requestLocationPermission() =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                getLastKnownLocation()

            } else {


                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionsRequestCode -> {
                val isPermissionsGranted = managePermissions
                    .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    getLastKnownLocation()

                    Toast.makeText(
                        context,
                        getString(R.string.permission_granted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, getString(R.string.need_permission), Toast.LENGTH_SHORT)
                        .show()
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