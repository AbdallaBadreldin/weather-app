package eg.iti.weatherapp.main.ui.map

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import eg.iti.weatherapp.R


class MapsFragment : Fragment(),  OnMapReadyCallback {
    lateinit var mapBtn: Button
    lateinit var latLong: LatLng
    lateinit var sharedPreferences: SharedPreferences
    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setOnMapClickListener {
            latLong = it
            mapBtn.visibility = View.VISIBLE
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title(getString(R.string.picked_location))
            )
        }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_maps, container, false)
        //shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        mapBtn = view.findViewById(R.id.map_button)
        mapBtn.setOnClickListener {

//            action_map_to_home
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            editor.putString(
                getString(R.string.preference_longitude),
                latLong?.longitude.toString()
            )
            editor.putString(getString(R.string.preference_alatitude), latLong?.latitude.toString())
            editor.apply()
            editor.commit()
            findNavController().navigate(ActionOnlyNavDirections(R.id.action_map_to_home)) //for test purposal only
        }
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}