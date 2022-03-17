package eg.iti.weatherapp.main.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDao
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDataBase

interface LocalSourceInterface  {

    //interface for  table
    fun getAllWeather() : LiveData<List<WeatherResponse>>
    fun getFavourtieWeather() :  LiveData<List<WeatherResponse>>
    fun getCurrentWeather() :  LiveData<List<WeatherResponse>>

    fun insertWeatherResponse(weatherResponse:WeatherResponse)
    fun deleteWeatherResponse(weatherResponse:WeatherResponse)
    fun deleteAllData()
    fun deleteAllCurrentWeatherResponse()
    fun deleteAllFavouriteWeatherResponse()

    fun updateWeatherResponse(weatherResponse:WeatherResponse)
}