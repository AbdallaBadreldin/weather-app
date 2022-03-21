package eg.iti.weatherapp.main.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


// here we add our interface for all local data
/*
when we will add new tables or add new methods in daos
we will modify localSource
*/
class LocalSource : LocalSourceInterface {

    companion object {
        private var weatherDataBase: WeatherDataBase? = null
//        private var storedWeatherResponse: LiveData<List<WeatherResponse>>? = null
//        private var storedFavouriteWeather: LiveData<List<WeatherResponse>>? = null
//        private var storedCurretWeather: LiveData<List<WeatherResponse>>? = null

        fun initializeDB(context: Context): WeatherDataBase = WeatherDataBase.Companion(context)

//                fun insertData(context: Context,weatherResponse: WeatherResponse){
//                        weatherDataBase = initializeDB(context = context)
//              CoroutineScope(IO).launch {
//                      val weatherDetails = weatherResponse
//                      weatherDataBase!!.weatherDao().insertWeatherResponse(weatherDetails)
//              }
//                }


    }

    override fun getAllWeatherResponse(context: Context): Flow<List<WeatherResponse>> {
        weatherDataBase = initializeDB(context = context)
        return weatherDataBase!!.weatherDao().getAllWeatherResponse()
    }

    override fun insertWeatherResponse(weatherResponse: WeatherResponse, context: Context) {
        weatherDataBase = initializeDB(context = context)
        CoroutineScope(IO).launch {
            val weatherDetails = weatherResponse
            weatherDataBase!!.weatherDao().insertWeatherResponse(weatherDetails)
        }
    }

    override fun deleteAllWeatherResponseData(context: Context) {
        weatherDataBase = initializeDB(context = context)
        CoroutineScope(IO).launch {
            weatherDataBase!!.weatherDao().clearAllWeatherResponse()
        }
    }

    //locstions daos
    override fun getAllLocations(context: Context): Flow<List<Location>> {
        weatherDataBase = initializeDB(context = context)
        return weatherDataBase!!.LocationDao().getAllLocation()
    }

    override fun insertLocation(location: Location, context: Context) {
        weatherDataBase = initializeDB(context = context)
        CoroutineScope(IO).launch {
            weatherDataBase!!.LocationDao().insertLocation(location)
        }
    }

    override fun deleteLocation(location: Location, context: Context) {
        weatherDataBase = initializeDB(context = context)
        CoroutineScope(IO).launch {
            weatherDataBase!!.LocationDao().deleteLocation(location)
        }
    }

    override fun getAllAlerts(context: Context): Flow<List<AlertNotification>> {
        weatherDataBase = initializeDB(context = context)
        return weatherDataBase!!.alertDao().getAllAlerts()
    }

    override fun insertAlert(alert: AlertNotification, context: Context) {
        weatherDataBase = initializeDB(context = context)
        CoroutineScope(IO).launch {
            weatherDataBase!!.alertDao().insertAlert(alert)
        }
    }

    override fun deleteAlert(alert: AlertNotification, context: Context) {
        weatherDataBase = initializeDB(context = context)
        CoroutineScope(IO).launch {
            weatherDataBase!!.alertDao().deleteAlert(alert)
        }
    }


    //work manager staff====================
    override fun getStoredDataForWorkManager(context: Context): List<WeatherResponse> {
        weatherDataBase = initializeDB(context = context)
        return  weatherDataBase!!.weatherDao().getAllWeatherResponseForWorkManager()
    }

    override fun getAllUserAlertsForWorkManager(context: Context): List<AlertNotification> {
        weatherDataBase = initializeDB(context = context)
        return  weatherDataBase!!.alertDao().getAllAlertsForWorkManager()
        }

    override fun getAlertsBetween(
        startTime: Long,
        endTime: Long,
        context: Context
    ): List<AlertNotification> {
        weatherDataBase = initializeDB(context = context)
        return  weatherDataBase!!.alertDao().getAlertsBetweenForWorkManager(startTime,endTime)
    }


}












    /*  override fun deleteWeatherResponse(weatherResponse: WeatherResponse, context: Context) {
          weatherDataBase = initializeDB(context = context)
          CoroutineScope(IO).launch {
              val weatherDetails = weatherResponse
              weatherDataBase!!.weatherDao().delete(weatherDetails)
          }
      }*/

    /*   override fun getFavourtieWeather(context: Context): Flow<List<WeatherResponse>> {
               weatherDataBase= initializeDB(context = context)
               return weatherDataBase!!.weatherDao().getFavouriteWeather()
       }
//
       override fun getCurrentWeather(context: Context): Flow<List<WeatherResponse>> {
               weatherDataBase= initializeDB(context = context)
               return weatherDataBase!!.weatherDao().getCurrentWeather()
       }*/




//        override  fun deleteAllCurrentWeatherResponse(context: Context) {
//                 weatherDataBase = initializeDB(context = context)
//                 CoroutineScope(IO).launch {
//
//                         weatherDataBase!!.weatherDao().deleteAllCurrent()
//                 }
//        }

//             override    fun deleteAllFavouriteWeatherResponse(context: Context) {
//                     weatherDataBase = initializeDB(context = context)
//                     CoroutineScope(IO).launch {
//
//                             weatherDataBase!!.weatherDao().deleteAllFavourite()
//                     }
//             }
//        override   fun updateWeatherResponse(weatherResponse: WeatherResponse,context: Context) {
//
//                 }




/*
        override fun getAllWeather():  LiveData<List<WeatherResponse>> {
                return storedWeather
        }

        override fun getFavourtieWeather(): LiveData<List<WeatherResponse>> {
                return storedFavouriteWeather
        }

        override fun getCurrentWeather(): LiveData<List<WeatherResponse>> {
              return storedCurretWeather
        }

        override fun insertWeatherResponse(weatherResponse: WeatherResponse) {
                weatherDao.insertWeatherResponse(weatherResponse)
        }

        override fun deleteWeatherResponse(weatherResponse: WeatherResponse) {
                weatherDao.delete(weatherResponse)
        }

        override fun deleteAllData() {
                weatherDao.clearALLOfflineData()
        }

        override fun deleteAllCurrentWeatherResponse() {
                weatherDao.deleteAllCurrent()
        }

        override fun deleteAllFavouriteWeatherResponse() {
                weatherDao.deleteAllFavourite()
        }

        override fun updateWeatherResponse(weatherResponse: WeatherResponse) {
                weatherDao.updateWeather()
        }*/


/*val weatherDataBase : WeatherDataBase = WeatherDataBase.getInstance(context)
weatherDao = weatherDataBase.weatehrDao()
storedWeather = weatherDao.getAllOfflineData()
storedFavouriteWeather = weatherDao.getFavouriteWeather()
storedCurretWeather = weatherDao.getCurrentWeather()

return this
}*/

