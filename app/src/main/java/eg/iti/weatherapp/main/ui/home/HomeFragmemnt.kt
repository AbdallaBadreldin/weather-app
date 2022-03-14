package eg.iti.weatherapp.main.ui.home

import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import eg.iti.weatherapp.databinding.HomeFragmentBinding
import eg.iti.weatherapp.main.data.database.LocalSource
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.ui.base.MyViewModelFactory
import eg.iti.weatherapp.main.ui.location.toast
import eg.iti.weatherapp.main.utils.DateUtils
import java.util.*
import kotlin.math.log


class HomeFragmemnt : Fragment() {
    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeFragmemntViewModel

    companion object {
        fun newInstance() = HomeFragmemnt()
    }
    lateinit var swipeContainer: SwipeRefreshLayout


    lateinit var txtWeather_discription :TextView
    lateinit var txtPressure :TextView
    lateinit var txtHumidity :TextView
    lateinit var txtWind :TextView
    lateinit var txtCloud :TextView
    lateinit var txtUltraViolet :TextView
    lateinit var txtVisibility :TextView
    lateinit var txtTimezone :TextView
    lateinit var txtDt :TextView
    lateinit var txtTemp :TextView
    lateinit var imgWeatherIcon :ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    viewModel = ViewModelProvider(this,MyViewModelFactory(
            MainRepository(LocalSource(),
            RemoteSource()
        ))).get(HomeFragmemntViewModel::class.java)

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bindLayoutComponents()

        //swipe things
        swipeContainer.setOnRefreshListener {
            //get data from remoteSource
            fetchTimelineAsync()
        }
        fetchTimelineAsync()

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);


        return root
//        inflater.inflate(R.layout.home_fragmemnt_fragment, container, false)
    }

    private fun bindLayoutComponents() {
        swipeContainer=binding.homeSwipeContainer
        txtWeather_discription=binding.weatherDiscription
        txtPressure=binding.pressure
        txtHumidity=binding.humidity
        txtWind = binding.wind
        txtCloud = binding.cloud
        txtUltraViolet =binding.ultraViolet
        txtVisibility =binding.visibility
        txtTimezone = binding.timezone
        txtDt = binding.dt
        txtTemp=binding.temp
        imgWeatherIcon=binding.weatherIcon
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun fetchTimelineAsync() {
        viewModel.getCurrentWeather()

        viewModel.currentWeather.observe(requireActivity()) {
            txtTimezone.text = it.timeZone
            txtDt.text = DateUtils(it.current.dayTime.toLong(), Locale.ENGLISH).convertDate()
            txtWeather_discription.text=it.current.weather[0].description
            txtTemp.text = it.current.temp
            txtPressure.text = it.current.pressure
            txtHumidity.text = it.current.humidity
            txtWind.text = it.current.windSpeed
            txtCloud.text = it.current.cloud
            txtUltraViolet.text = it.current.ultraViolet
            txtVisibility.text = it.current.visibility

            swipeContainer.setRefreshing(false)
        }
        viewModel.errorMessage.observe(requireActivity(), Observer {
            context?.toast(it)
            swipeContainer.setRefreshing(false)
        })
                // Remember to CLEAR OUT old items before appending in the new ones
//                adapter.clear()
                // ...the data has come back, add new items to your adapter...
//                adapter.addAll(...)
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false)
            }


}