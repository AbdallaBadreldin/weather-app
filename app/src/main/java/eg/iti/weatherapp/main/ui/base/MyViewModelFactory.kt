package eg.iti.weatherapp.main.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.ui.alert.AlertViewModel
import eg.iti.weatherapp.main.ui.favourite.FavouriteViewModel
import eg.iti.weatherapp.main.ui.favouritemap.FavouriteMapViewModel
import eg.iti.weatherapp.main.ui.home.HomeViewModel

class MyViewModelFactory constructor(private val repository: MainRepository ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            FavouriteViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(FavouriteMapViewModel::class.java)) {
            FavouriteMapViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            AlertViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}