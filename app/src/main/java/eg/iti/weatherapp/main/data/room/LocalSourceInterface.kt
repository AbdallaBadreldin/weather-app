package eg.iti.weatherapp.main.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDao
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDataBase
import kotlinx.coroutines.flow.Flow

interface LocalSourceInterface  {

    //interface for  weatherResponse table
    fun getAllWeatherResponse(context: Context) : Flow<List<WeatherResponse>>
    fun insertWeatherResponse(weatherResponse:WeatherResponse,context: Context)
    fun deleteAllWeatherResponseData(context: Context)


    //interface for favourite locations table
    fun getAllLocations(context: Context) : Flow<List<Location>>
    fun insertLocation(location: Location,context: Context)
    fun deleteLocation(location: Location,context: Context)


    //interface for alerts notification table
    fun getAllAlerts(context: Context) : Flow<List<AlertNotification>>
    fun insertAlert(alert: AlertNotification,context: Context)
    fun deleteAlert(alert: AlertNotification,context: Context)



    //============== interface for WORKMANGER
    fun getStoredDataForWorkManager(context: Context): List<WeatherResponse>
    fun getAllUserAlertsForWorkManager(context: Context): List<AlertNotification>
    fun getAlertsBetween(startTime:Long,endTime:Long ,context: Context):List<AlertNotification>

//                            fun deleteAllLocations

//    fun getFavourtieWeather(context: Context) :  Flow<List<WeatherResponse>>
//    fun getCurrentWeather(context: Context) : Flow<List<WeatherResponse>>


//    fun deleteWeatherResponse(weatherResponse:WeatherResponse,context: Context)

//    fun deleteAllCurrentWeatherResponse(context: Context)
//    fun deleteAllFavouriteWeatherResponse(context: Context)

//    fun updateWeatherResponse(weatherResponse:WeatherResponse,context: Context)
}