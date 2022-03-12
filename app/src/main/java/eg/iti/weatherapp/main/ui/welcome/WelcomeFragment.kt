package eg.iti.weatherapp.main.ui.welcome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.WelcomeFragmentBinding
import eg.iti.weatherapp.main.ui.location.PickLocationFragment

class WelcomeFragment : Fragment() {

    private var _binding:WelcomeFragmentBinding? =null
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


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}