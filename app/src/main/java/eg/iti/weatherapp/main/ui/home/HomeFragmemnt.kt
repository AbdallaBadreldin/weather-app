package eg.iti.weatherapp.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import eg.iti.weatherapp.databinding.HomeFragmentBinding


class HomeFragmemnt : Fragment() {
    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeFragmemntViewModel

    companion object {
        fun newInstance() = HomeFragmemnt()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeFragmemntViewModel::class.java)
        //todo with view model

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
//        inflater.inflate(R.layout.home_fragmemnt_fragment, container, false)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}