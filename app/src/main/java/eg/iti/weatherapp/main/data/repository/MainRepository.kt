package eg.iti.weatherapp.main.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
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
    fun getCurrentWeatherByLocation(lat:String,lon:String,unit:String,lang:String,API:String)
= remoteSource.getCurrent(lat,lon,unit,lang,API)


    //offline
    fun insertWeatherResponseToDatabase(weather: WeatherResponse ,context: Context) {
        localSource.insertWeatherResponse(weather,context)
    }

    fun deleteWeatherResponseFromDatabase(weather :WeatherResponse,context:Context) {
        localSource.deleteWeatherResponse(weather,  context)
    }
    fun deleteCurrentWeather(context: Context){
        localSource.deleteAllCurrentWeatherResponse(context)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
   fun getCurrentWeatherFromDataBase (context: Context) : Flow<List<WeatherResponse>> =
       localSource.getCurrentWeather(context = context)



//   }=localSource.getCurrentWeather(context )
}