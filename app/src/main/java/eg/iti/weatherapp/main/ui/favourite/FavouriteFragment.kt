package eg.iti.weatherapp.main.ui.favourite

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.FavouriteFragmentBinding
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.ui.base.MyViewModelFactory

class FavouriteFragment : Fragment() {
    private var _binding: FavouriteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit  var sharedPreferences : SharedPreferences
    lateinit var  favouriteAdapter :AlertView

    private lateinit var viewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                MainRepository(
                    LocalSource(),
                    RemoteSource()
                )
            )
        ).get(FavouriteViewModel::class.java)

        _binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favouriteAdapter=AlertView(viewModel)
        binding.favRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = favouriteAdapter
        }
        viewModel.getAllLocationData(requireContext()).observe(viewLifecycleOwner, Observer {
            favouriteAdapter.setFavouriteList(it)
            favouriteAdapter.notifyDataSetChanged()
        })

        binding.favAddFab.setOnClickListener{
            findNavController().navigate(ActionOnlyNavDirections(R.id.navigation_favourite_map) )   }
        return root
    }



}