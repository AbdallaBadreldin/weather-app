package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import androidx.lifecycle.LiveData
import androidx.room.*
import eg.iti.weatherapp.main.data.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherResponse")
    fun getAllWeatherResponse(): Flow<List<WeatherResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherResponse(weather: WeatherResponse)

    //dangerous command which will always be used :) nuke all data all over THE DATABASE LIKE A SMALL PIECE OF BUG CRASHED BY A PANZER FROM HELL ..
    @Query("DELETE FROM WeatherResponse")
    fun clearAllWeatherResponse()

    @Delete
    fun delete(weather: WeatherResponse)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeather(vararg weather: WeatherResponse)


    //==================WorkManager Staff==================
    @Query("SELECT * FROM WeatherResponse")
    fun getAllWeatherResponseForWorkManager() : List<WeatherResponse>


}