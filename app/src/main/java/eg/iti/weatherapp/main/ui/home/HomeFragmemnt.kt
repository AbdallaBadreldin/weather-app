package eg.iti.weatherapp.main.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(this).get(HomeFragmemntViewModel::class.java)
        viewModel = ViewModelProvider(this,MyViewModelFactory(
            MainRepository(LocalSource(),
            RemoteSource()
        ))).get(HomeFragmemntViewModel::class.java)

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.currentWeather.observe(requireActivity(),{
            Toast.makeText(requireContext(), it.timeZone, Toast.LENGTH_SHORT).show()
        })
        viewModel.errorMessage.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getCurrentWeather()


        //swipe things
        swipeContainer=binding.homeSwipeContainer
        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
//            fetchTimelineAsync(0)
            viewModel.getCurrentWeather()
            fetchTimelineAsync()
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        return root
//        inflater.inflate(R.layout.home_fragmemnt_fragment, container, false)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun fetchTimelineAsync() {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
//        client.getHomeTimeline(object: JsonHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: okhttp3.Headers, json: JSON) {
                // Remember to CLEAR OUT old items before appending in the new ones
//                adapter.clear()
                // ...the data has come back, add new items to your adapter...
//                adapter.addAll(...)
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false)
            }
//            override fun onFailure(
//                statusCode: Int,
//                headers: okhttp3.Headers,
//                response: String,
//                throwable: Throwable
//            ) {
//                Log.d("DEBUG", "Fetch timeline error", throwable)
//            }
//        })
//    }
}