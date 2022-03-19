package eg.iti.weatherapp.main.ui.home


import android.R
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.layoutDirection
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import eg.iti.weatherapp.databinding.HomeFragmentBinding
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.ui.base.MyViewModelFactory
import eg.iti.weatherapp.main.ui.location.toast
import eg.iti.weatherapp.main.utils.DateUtils
import eg.iti.weatherapp.main.utils.LocaleUtil
import eg.iti.weatherapp.main.utils.LocaleUtil.Companion.translateEnglishToArabic
import retrofit2.Response.error
import java.util.*




class HomeFragmemnt : Fragment() {
    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeFragmemntViewModel
    private lateinit  var sharedPreferences :SharedPreferences

    /*  companion object {
          fun newInstance() = HomeFragmemnt()
          fun oldInstance() = this
      }*/

    lateinit var swipeContainer: SwipeRefreshLayout

    lateinit var txtWeather_discription: TextView
    lateinit var txtPressure: TextView
    lateinit var txtHumidity: TextView
    lateinit var txtWind: TextView
    lateinit var txtCloud: TextView
    lateinit var txtUltraViolet: TextView
    lateinit var txtVisibility: TextView
    lateinit var txtTimezone: TextView
    lateinit var txtDt: TextView
    lateinit var txtTemp: TextView
    lateinit var imgWeatherIcon: ImageView

    val dailyAdapter = DailyAdapter()
    val hourlyAdapter = HourlyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences =PreferenceManager.getDefaultSharedPreferences(requireContext())
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                MainRepository(
                    LocalSource(),
                    RemoteSource()
                )
            )
        ).get(HomeFragmemntViewModel::class.java)

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //navigation drawer-----------------------------------------------------------------

        setupSideDrawer()

        binding.dailyRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = dailyAdapter
        }

        binding.hourlyRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.HORIZONTAL
            adapter = hourlyAdapter
        }

        bindLayoutComponents()

        //swipe things
        swipeContainer.setOnRefreshListener {
            //get data from remoteSource
            fetchTimelineAsync()
        }
        fetchTimelineAsync()

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            R.color.holo_blue_bright,
            R.color.holo_green_light,
            R.color.holo_orange_light,
            R.color.holo_red_light
        )

        return root
//        inflater.inflate(R.layout.home_fragmemnt_fragment, container, false)
    }

    private fun bindLayoutComponents() {
        swipeContainer = binding.homeSwipeContainer
        txtWeather_discription = binding.weatherDiscription
        txtPressure = binding.pressure
        txtHumidity = binding.humidity
        txtWind = binding.wind
        txtCloud = binding.cloud
        txtUltraViolet = binding.ultraViolet
        txtVisibility = binding.visibility
        txtTimezone = binding.timezone
        txtDt = binding.dt
        txtTemp = binding.temp
        imgWeatherIcon = binding.homeCurrentWeatherIcon
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun fetchTimelineAsync() {
        viewModel.getCurrentWeather(requireActivity())

        viewModel.currentWeather.observe(requireActivity()) {
            setDataIntoLayout(it)
            swipeContainer.isRefreshing = false
        }
        viewModel.errorMessage.observe(requireActivity(), Observer {
            context?.toast(getString(eg.iti.weatherapp.R.string.need_internet))

            viewModel.getOfflineStoredData(requireContext()).observe(viewLifecycleOwner)
            {
                if (it.size == 0)
                    Toast.makeText(context, getString(eg.iti.weatherapp.R.string.need_internet), Toast.LENGTH_LONG).show()
                else
                setDataIntoLayout(it[0])
            }

            swipeContainer.isRefreshing = false
        })
        // Remember to CLEAR OUT old items before appending in the new ones
//                adapter.clear()
        // ...the data has come back, add new items to your adapter...
//                adapter.addAll(...)
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.isRefreshing = false
    }

    fun setDataIntoLayout(it: WeatherResponse?) {
  var timeZone =LocaleUtil.getCityName(it!!.lat.toDouble() , it.lon.toDouble(),requireContext() )
        if (timeZone == getString(eg.iti.weatherapp.R.string.def))
          txtTimezone.text = it?.timeZone
        else
            txtTimezone.text = timeZone

        txtDt.text =
            it?.current?.dayTime?.let { it1 ->
                DateUtils.convertDate(it1.toLong(),Locale.getDefault())
            }
        txtWeather_discription.text = it?.current!!.weather[0].description
        txtTemp.text = translateEnglishToArabic(it.current.temp,requireContext())
        txtPressure.text = translateEnglishToArabic(it.current.pressure,requireContext())
        txtHumidity.text = translateEnglishToArabic(it.current.humidity,requireContext())
        txtWind.text = translateEnglishToArabic(it.current.windSpeed,requireContext())
        txtCloud.text = translateEnglishToArabic(it.current.cloud,requireContext())
        txtUltraViolet.text = translateEnglishToArabic(it.current.ultraViolet,requireContext())
        txtVisibility.text = translateEnglishToArabic(it.current.visibility,requireContext())

        //update adapters adapter
        dailyAdapter.setDailyList(it.daily)
        dailyAdapter.notifyDataSetChanged()
//
        hourlyAdapter.setHourlyListItems(it.hourly)
        hourlyAdapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        activity?.window?.decorView?.layoutDirection = Locale.getDefault().layoutDirection
   fetchTimelineAsync()
    }

    fun setupSideDrawer() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.homeAppBarMain.homeToolbar)
//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        val drawerLayout: DrawerLayout = binding.homeDrawerLayout
        val navView: NavigationView = binding.homeNavView

        val navController = this.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
              eg.iti.weatherapp.R.id.navigation_about,
               eg.iti.weatherapp.R.id.navigation_setting,
                eg.iti.weatherapp.R.id.navigation_home,
                eg.iti.weatherapp.R.id.navigation_alert,
                eg.iti.weatherapp.R.id.navigation_favourite
            ), drawerLayout
        )
        setupActionBarWithNavController(
            activity as AppCompatActivity,
            navController,
            appBarConfiguration
        )
        navView.setupWithNavController(navController)
    }

}