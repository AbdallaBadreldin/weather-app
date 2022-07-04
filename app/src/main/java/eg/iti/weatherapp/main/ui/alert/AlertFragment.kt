package eg.iti.weatherapp.main.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.ui.base.MyViewModelFactory

class AlertFragment : Fragment() {

//    companion object {
//        fun newInstance() = AlertFragment()
//    }
private lateinit var adapterAlert: AlertAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlertViewModel
    private lateinit var fab :FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view= inflater.inflate(R.layout.alert_fragment, container, false)
        fab =view.findViewById(R.id.alert_add_fab)
        fab.setOnClickListener{showEditDialog()}
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                MainRepository(
                    LocalSource(),
                    RemoteSource()
                )
            )
        ).get(AlertViewModel::class.java)
adapterAlert= AlertAdapter(viewModel)
       recyclerView= view.findViewById(R.id.alert_recycler_view)
          recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterAlert
        }

        viewModel.getAllData(requireContext()).observe(this@AlertFragment.viewLifecycleOwner , androidx.lifecycle.Observer {
            adapterAlert.setAlertList(it)
            adapterAlert.notifyDataSetChanged()
        })

        return view
    }

    private fun showEditDialog() {
        AlertDialogFragment().show(childFragmentManager, "")
    }


}