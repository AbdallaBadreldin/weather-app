package eg.iti.weatherapp.main.ui.favourite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository

class FavouriteViewModel(private val repository: MainRepository) : ViewModel() {

    fun getAllLocationData(context : Context) : LiveData<List<Location>>
            =repository.getAllLocationFromDataBAse(context = context).asLiveData()

    fun insertLocation(location: Location,context : Context) {
        repository.insertLocationToDatabase(location,context)
    }


    fun deleteLocation(location: Location,context : Context) {
        repository.deleteLocation(location, context)
    }



}