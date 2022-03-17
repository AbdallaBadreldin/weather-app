package eg.iti.weatherapp.main.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDao
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDataBase


// here we add our interface for all local data
/*
when we will add new tables or add new methods in daos
we will modify localSource
*/
class LocalSource : LocalSourceInterface{

        companion object{
                private lateinit var weatherDao: WeatherDao
                private lateinit var storedWeather : LiveData<List<WeatherResponse>>
                private lateinit var storedFavouriteWeather : LiveData<List<WeatherResponse>>
                private lateinit var storedCurretWeather : LiveData<List<WeatherResponse>>

                fun initializeDB(context: Context) : WeatherDataBase {
                        val weatherDataBase : WeatherDataBase = WeatherDataBase.getInstance(context)
                        weatherDao = weatherDataBase.weatehrDao()
                        storedWeather = weatherDao.getAllOfflineData()
                        storedFavouriteWeather = weatherDao.getFavouriteWeather()
                        storedCurretWeather = weatherDao.getCurrentWeather()

                        return weatherDao as WeatherDataBase
                }
        }

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
                weatherDao.insertAll(weatherResponse)
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
        }
}

