package eg.iti.weatherapp.main.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDao
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
class LocalSource : LocalSourceInterface{

        companion object{
                private var weatherDataBase: WeatherDataBase? =null
                private var storedWeather : LiveData<List<WeatherResponse>>? =null
                private var storedFavouriteWeather : LiveData<List<WeatherResponse>>? = null
                private var storedCurretWeather : LiveData<List<WeatherResponse>>? = null

                fun initializeDB(context: Context) : WeatherDataBase =  WeatherDataBase.Companion(context)

                fun insertData(context: Context,weatherResponse: WeatherResponse){
                        weatherDataBase = initializeDB(context = context)
              CoroutineScope(IO).launch {
                      val weatherDetails = weatherResponse
                      weatherDataBase!!.weatherDao().insertWeatherResponse(weatherDetails)
              }
                }
        }

        override fun getAllWeather(context: Context): Flow<List<WeatherResponse>> {
              weatherDataBase= initializeDB(context = context)
                return weatherDataBase!!.weatherDao().getAllOfflineData()
        }

        override fun getFavourtieWeather(context: Context): Flow<List<WeatherResponse>> {
                weatherDataBase= initializeDB(context = context)
                return weatherDataBase!!.weatherDao().getFavouriteWeather()
        }
//
        override fun getCurrentWeather(context: Context): Flow<List<WeatherResponse>> {
                weatherDataBase= initializeDB(context = context)
                return weatherDataBase!!.weatherDao().getCurrentWeather()
        }

        override fun insertWeatherResponse(weatherResponse: WeatherResponse,context: Context) {
                weatherDataBase = initializeDB(context = context)
                CoroutineScope(IO).launch {
                        val weatherDetails = weatherResponse
                        weatherDataBase!!.weatherDao().insertWeatherResponse(weatherDetails)
        }}

     override   fun deleteWeatherResponse(weatherResponse: WeatherResponse,context: Context) {
                weatherDataBase = initializeDB(context = context)
                CoroutineScope(IO).launch {
                        val weatherDetails = weatherResponse
                        weatherDataBase!!.weatherDao().delete(weatherDetails)
        }
        }

        override fun deleteAllData(context: Context) {
//                TODO("Not yet implemented")
        }


        override  fun deleteAllCurrentWeatherResponse(context: Context) {
//                 weatherDataBase = initializeDB(context = context)
//                 CoroutineScope(IO).launch {
//
//                         weatherDataBase!!.weatherDao().deleteAllCurrent()
//                 }
        }

             override    fun deleteAllFavouriteWeatherResponse(context: Context) {
//                     weatherDataBase = initializeDB(context = context)
//                     CoroutineScope(IO).launch {
//
//                             weatherDataBase!!.weatherDao().deleteAllFavourite()
//                     }
             }
        override   fun updateWeatherResponse(weatherResponse: WeatherResponse,context: Context) {
                         TODO("Not yet implemented")
                 }



         }

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
