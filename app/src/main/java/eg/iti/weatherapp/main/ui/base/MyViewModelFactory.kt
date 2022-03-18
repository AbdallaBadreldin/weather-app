package eg.iti.weatherapp.main.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.ui.home.HomeFragmemntViewModel

class MyViewModelFactory constructor(private val repository: MainRepository ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeFragmemntViewModel::class.java)) {
            HomeFragmemntViewModel(this.repository ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}