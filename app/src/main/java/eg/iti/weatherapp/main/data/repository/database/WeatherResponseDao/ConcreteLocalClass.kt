package eg.iti.weatherapp.main.data.repository.database.WeatherResponseDao

import android.content.Context
import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.database.LocalSource

class ConcreteLocalClass : LocalSource {

     companion object{
         private lateinit var weatherDao:WeatherDao
         private lateinit var storedWeather :LiveData<WeatherResponse>

         fun initializeDB(context: Context) :WeatherDataBase{
           val weatherDataBase :WeatherDataBase = WeatherDataBase.getInstance(context)
             weatherDao = weatherDataBase.weatehrDao()
           storedWeather = weatherDao.getAllOfflineData()

             return weatherDao as WeatherDataBase
         }

     }


    override fun getAllWeather(): LiveData<WeatherResponse> {
       return storedWeather
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

    override fun updateWeatherResponse(weatherResponse: WeatherResponse) {
        weatherDao.updateWeather()
    }
}