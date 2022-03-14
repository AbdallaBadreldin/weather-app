package eg.iti.weatherapp.main.data.repository.database.WeatherResponseDao

import androidx.lifecycle.LiveData
import androidx.room.*
import eg.iti.weatherapp.main.data.model.WeatherResponse

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_response")
    fun getAllOfflineData(): LiveData<WeatherResponse>

    @Insert
    fun insertAll(vararg weather: WeatherResponse)

    @Delete
    fun delete(weather: WeatherResponse)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeather(vararg weather: WeatherResponse)

    //dangerous command which will always be used :) nuke all data all over THE DATABASE LIKE A SMALL PIECE OF BUG CRASHED BY A PANZER FROM HELL ..
    @Query("DELETE FROM Weather_Response")
    fun clearALLOfflineData()
}