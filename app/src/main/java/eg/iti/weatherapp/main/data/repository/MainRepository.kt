package eg.iti.weatherapp.main.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.model.Weather
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.data.room.LocalSource.Companion.initializeDB
import eg.iti.weatherapp.main.data.room.LocalSourceInterface
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDao
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDataBase
import kotlinx.coroutines.flow.Flow


class MainRepository ( private val localSource: LocalSource,private val remoteSource : RemoteSource){
//private val localSource = initializeDB(context = context) as LocalSourceInterface

  //online
    fun getCurrentWeatherByLocation(lat:String,lon:String,lang:String,API:String)
= remoteSource.getCurrent(lat,lon,lang,API)


    //offline weatherResponse
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAllWeatherResponseFromDataBAse (context: Context) : Flow<List<WeatherResponse>> =
        localSource.getAllWeatherResponse(context = context)


    fun insertWeatherResponseToDatabase(weather: WeatherResponse ,context: Context) {
        localSource.insertWeatherResponse(weather,context)
    }


    fun clearAllWeatherResponseData(context: Context){
        localSource.deleteAllWeatherResponseData(context)
    }

    //offline favourite stored location data
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAllLocationFromDataBAse (context: Context) : Flow<List<Location>> =
        localSource.getAllLocations(context = context)


    fun insertLocationToDatabase(location: Location ,context: Context) {
        localSource.insertLocation(location ,context)
    }

    fun deleteLocation(location:Location , context: Context){
        localSource.deleteLocation(location,context)
    }


//    fun deleteWeatherResponseFromDatabase(weather :WeatherResponse,context:Context) {
//        localSource.deleteWeatherResponse(weather,  context)
//    }




//   }=localSource.getCurrentWeather(context )
}