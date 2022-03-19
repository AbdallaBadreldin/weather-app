package eg.iti.weatherapp.main.ui.favouritemap

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.repository.MainRepository

class FavouriteMapViewModel(private var repository: MainRepository)  : ViewModel() {


    //we  may use it latter to drop markers on map for favourite places
//    fun getAllLocationData(context : Context) : LiveData<List<Location>>
//            =repository.getAllLocationFromDataBAse(context = context).asLiveData()


    //need it now
    fun insertLocation(location: Location, context: Context) {
        repository.insertLocationToDatabase(location, context)
    }


    //more advanced but if there's time before deadline we may implement it
//    fun deleteLocation(location: Location, context: Context) {
//        repository.deleteLocation(location, context)
//    }
}