package eg.iti.weatherapp.main.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import eg.iti.weatherapp.main.data.model.AlertNotification
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


    //offline favourite stored alerts data
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAllAlertsFromDataBAse (context: Context) : Flow<List<AlertNotification>> =
        localSource.getAllAlerts(context = context)


    fun insertAlertToDatabase(alert:AlertNotification,context: Context) {
        localSource.insertAlert(alert ,context)
    }

    fun deleteAlert(alert:AlertNotification , context: Context){
        localSource.deleteAlert(alert,context)
    }


//============================================================= for work manager
fun getAllStoredUsersAlertsForWorkManager(context: Context): List<AlertNotification> {
return localSource.getAllUserAlertsForWorkManager(context)
}

    fun getAllStoredWeatherDataForWorkManager(context: Context): List<WeatherResponse> {
        return localSource.getStoredDataForWorkManager(context)
    }


fun getAlertsBetween(startTime :Long ,endTime: Long,context: Context ): List<AlertNotification> {
    return localSource.getAlertsBetween(startTime ,endTime ,context)
}


}