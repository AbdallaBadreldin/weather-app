package eg.iti.weatherapp.main.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDao
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDataBase
import kotlinx.coroutines.flow.Flow

interface LocalSourceInterface  {

    //interface for  table
    fun getAllWeather(context: Context) : Flow<List<WeatherResponse>>
    fun getFavourtieWeather(context: Context) :  Flow<List<WeatherResponse>>
    fun getCurrentWeather(context: Context) : Flow<List<WeatherResponse>>

    fun insertWeatherResponse(weatherResponse:WeatherResponse,context: Context)
    fun deleteWeatherResponse(weatherResponse:WeatherResponse,context: Context)
    fun deleteAllData(context: Context)
    fun deleteAllCurrentWeatherResponse(context: Context)
    fun deleteAllFavouriteWeatherResponse(context: Context)

    fun updateWeatherResponse(weatherResponse:WeatherResponse,context: Context)
}