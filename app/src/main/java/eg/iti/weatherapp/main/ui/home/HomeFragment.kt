package eg.iti.weatherapp.main.ui.home


import android.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.layoutDirection
import androidx.core.view.GravityCompat
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
import java.math.MathContext
import java.util.*


class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPreferences: SharedPreferences

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
        setHasOptionsMenu(true)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                MainRepository(
                    LocalSource(),
                    RemoteSource()
                )
            )
        ).get(HomeViewModel::class.java)

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
        viewModel.getCurrentWeather(context = requireContext())

        viewModel.currentWeather.observe(requireActivity()) {
            setDataIntoLayout(it)
            swipeContainer.isRefreshing = false
        }
        viewModel.errorMessage.observe(requireActivity(), Observer {
            context?.toast(getString(eg.iti.weatherapp.R.string.need_internet))

            viewModel.getOfflineStoredData(requireContext()).observe(viewLifecycleOwner)
            {
                if (it.size == 0)
                    Toast.makeText(
                        context,
                        getString(eg.iti.weatherapp.R.string.need_internet),
                        Toast.LENGTH_LONG
                    ).show()
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
        var timeZone =
            LocaleUtil.getCityName(it!!.lat.toDouble(), it.lon.toDouble(), requireContext())
        if (timeZone == getString(eg.iti.weatherapp.R.string.def))
            txtTimezone.text = it.timeZone
        else
            txtTimezone.text = timeZone

        txtDt.text =
            it.current.dayTime.let { it1 ->
                DateUtils.convertDate(it1.toLong() * 1000, Locale.getDefault())
            }
        imgWeatherIcon.setImageResource(pickPhoto(it.current.weather[0].icon))
        txtWeather_discription.text = it.current.weather[0].description
        txtTemp.text = translateEnglishToArabic(
            convertTempAsSharedPreferences(it.current.temp),
            requireContext()
        )       //c f k
        txtPressure.text = translateEnglishToArabic(
            it.current.pressure,
            requireContext()
        ) + getString(eg.iti.weatherapp.R.string.hpa_qoutation)  //hpa
        txtHumidity.text = translateEnglishToArabic(
            it.current.humidity,
            requireContext()
        ) + getString(eg.iti.weatherapp.R.string.percent_qoutation)
        txtWind.text = translateEnglishToArabic(
            convertWindSpeedAsSharedPreference(it.current.windSpeed),
            requireContext()
        )        //m/s
        txtCloud.text = translateEnglishToArabic(
            it.current.cloud,
            requireContext()
        ) + getString(eg.iti.weatherapp.R.string.percent_qoutation)
        txtUltraViolet.text =
            translateEnglishToArabic(it.current.ultraViolet, requireContext())   //just nathing
        txtVisibility.text = translateEnglishToArabic(
            it.current.visibility,
            requireContext()
        ) + getString(eg.iti.weatherapp.R.string.meter)

        //update adapters adapter
        dailyAdapter.setDailyList(it.daily)
        dailyAdapter.notifyDataSetChanged()
//
        hourlyAdapter.setHourlyListItems(it.hourly)
        hourlyAdapter.notifyDataSetChanged()

    }

    fun convertTempAsSharedPreferences(temp: String): String {
        val tempo = sharedPreferences.getString(
            getString(eg.iti.weatherapp.R.string.preference_temperature),
            getString(eg.iti.weatherapp.R.string.pref_kelvin)
        )
        when (tempo) {
            getString(eg.iti.weatherapp.R.string.pref_kelvin) -> return temp + getString(eg.iti.weatherapp.R.string.unit_kelvin)
            getString(eg.iti.weatherapp.R.string.pref_celsius) -> return (temp.toDouble() - 273.15).toBigDecimal(
                MathContext(2)
            ).toString() + getString(eg.iti.weatherapp.R.string.unit_celsuis) //°C
            getString(eg.iti.weatherapp.R.string.pref_fahrenheit) -> return ((temp.toDouble() * 9 / 5 - 459.67).toBigDecimal(
                MathContext(2)
            )).toString() + getString(eg.iti.weatherapp.R.string.unit_fahrenhiet)
        }
        return temp
    }

    fun convertWindSpeedAsSharedPreference(speed: String): String {
        val speedo = sharedPreferences.getString(
            getString(eg.iti.weatherapp.R.string.preference_windSpeed),
            getString(eg.iti.weatherapp.R.string.pref_meterPerSecond)
        ).toString()
        when (speedo) {
            getString(eg.iti.weatherapp.R.string.pref_meterPerSecond) -> return (speed + getString(
                eg.iti.weatherapp.R.string.meter_per_sec
            ))
            getString(eg.iti.weatherapp.R.string.pref_milePerHour) -> return ((speed.toDouble() * 2.23694).toBigDecimal(
                MathContext(2)
            ).toString() + getString(eg.iti.weatherapp.R.string.mile_per_hour))
            else -> return (speed + getString(eg.iti.weatherapp.R.string.meter_per_sec)).toString()
        }
        return speed
    }

    fun pickPhoto(image: String): Int {
        when (image) {
            "01d" -> return eg.iti.weatherapp.R.drawable.oned
            "01n" -> return eg.iti.weatherapp.R.drawable.onen
            "02d" -> return eg.iti.weatherapp.R.drawable.twod
            "02n" -> return eg.iti.weatherapp.R.drawable.twon
            "03d" -> return eg.iti.weatherapp.R.drawable.threed
            "03n" -> return eg.iti.weatherapp.R.drawable.threen
            "04d" -> return eg.iti.weatherapp.R.drawable.fourd
            "04n" -> return eg.iti.weatherapp.R.drawable.fourn
            "09d" -> return eg.iti.weatherapp.R.drawable.nined
            "09n" -> return eg.iti.weatherapp.R.drawable.ninen
            "10d" -> return eg.iti.weatherapp.R.drawable.tend
            "10n" -> return eg.iti.weatherapp.R.drawable.tenn
            "11d" -> return eg.iti.weatherapp.R.drawable.eleven_d
            "11n" -> return eg.iti.weatherapp.R.drawable.eleven_n
            "13d" -> return eg.iti.weatherapp.R.drawable.thirteen_d
            "13n" -> return eg.iti.weatherapp.R.drawable.thirteen_n
            "50d" -> return eg.iti.weatherapp.R.drawable.fifty_d
            "50n" -> return eg.iti.weatherapp.R.drawable.fifty_n
            else -> return eg.iti.weatherapp.R.drawable.oned
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            binding.homeDrawerLayout.hashCode()-> {
        binding.homeDrawerLayout.openDrawer(GravityCompat.START)
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        return true
//            }
//        }
        return super.onOptionsItemSelected(item)
    }

}