package eg.iti.weatherapp.main.ui.favouritemap

import android.content.SharedPreferences
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.ui.base.MyViewModelFactory
import eg.iti.weatherapp.main.ui.favourite.FavouriteViewModel
import eg.iti.weatherapp.main.utils.LocaleUtil

class FavouriteMapFragment : Fragment()   {

    lateinit var mapBtn: Button
    lateinit var latLong: LatLng
    lateinit var sharedPreferences: SharedPreferences
    lateinit var viewModel: FavouriteMapViewModel
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
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite_map, container, false)
        //shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                MainRepository(
                    LocalSource(),
                    RemoteSource()
                )
            )
        ).get(FavouriteMapViewModel::class.java)


        mapBtn = view.findViewById(R.id.fav_map_button)
        mapBtn.setOnClickListener {
viewModel.insertLocation(
    location = Location(
    latLong.latitude.toString(),
    latLong.longitude.toString(),
   LocaleUtil.getCityName(lat = latLong.latitude , lon = latLong.longitude,requireContext())
)
,requireContext())

Navigation.findNavController(view).navigateUp()

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.fav_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


}