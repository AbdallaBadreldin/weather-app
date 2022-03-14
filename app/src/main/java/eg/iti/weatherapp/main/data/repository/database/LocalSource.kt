package eg.iti.weatherapp.main.data.repository.database

import androidx.lifecycle.LiveData
import eg.iti.weatherapp.main.data.model.WeatherResponse


// here we add our interface for all local data
/*
when we will add new tables or add new methods in daos
we will modify localSource
*/
interface LocalSource {

        //interface for  table
        fun getAllWeather() : LiveData<WeatherResponse>

        fun insertWeatherResponse(weatherResponse:WeatherResponse)
        fun deleteWeatherResponse(weatherResponse:WeatherResponse)
        fun deleteAllData()
        fun updateWeatherResponse(weatherResponse:WeatherResponse)
    }

