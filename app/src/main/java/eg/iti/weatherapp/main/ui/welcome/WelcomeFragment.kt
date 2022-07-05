package eg.iti.weatherapp.main.ui.welcome

import android.icu.util.TimeUnit
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.work.*

import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.WelcomeFragmentBinding
import eg.iti.weatherapp.main.ui.workmanager.NotificationWorker
import java.util.*

import javax.xml.datatype.DatatypeConstants.MINUTES
import kotlin.time.DurationUnit

class WelcomeFragment : Fragment() {

    private var _binding: WelcomeFragmentBinding? =null
    companion object {
        fun newInstance() = WelcomeFragment()
    }
    private val binding get() =_binding!!
    private lateinit var viewModel: WelcomeViewModel

    lateinit var  btnNext : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        // TODO: Use the ViewModel

        _binding = WelcomeFragmentBinding.inflate(inflater,container,false)
        val root :View =binding.root

        binding.button.setOnClickListener {
//            findNavController().navigate(ActionOnlyNavDirections(R.id.action_welcome_to_picklocation) ) //for test purposal only
            val navHostFragment =
            parentFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as WelcomeFragment
            val navController = navHostFragment.findNavController()
            navController.navigate(R.id.navigation_pickLocation)  //can be action
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        if(!sharedPreferences.getString(getString(R.string.preference_longitude),"non").equals("non")) {
            findNavController().navigate(ActionOnlyNavDirections(R.id.action_welcome_to_home))
        }

//        WorkManager.getInstance(requireContext()).cancelAllWork()
//        startWorkManager()

//        val blurRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
//            .build()
//
//        workManager.enqueue(blurRequest)
//
//
//        val myWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
//            .setInitialDelay(2, java.util.concurrent.TimeUnit.MINUTES)
//            .build()
//        workManager.enqueue(myWorkRequest)


//        Locale.getDefault()


        return root
    }

fun startWorkManager(){
    val workManager = WorkManager.getInstance(activity?.applicationContext!!)
    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()
//    val data = Data.Builder()
//        data.putString(ENDPOINT_REQUEST, endPoint)
    val work = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(24, java.util.concurrent.TimeUnit.HOURS)
        .setConstraints(constraints)
//        .setInputData(data.build())
        .build()
    workManager.enqueue(work)
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

