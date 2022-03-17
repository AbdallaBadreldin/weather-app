package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import androidx.lifecycle.LiveData
import androidx.room.*
import eg.iti.weatherapp.main.data.model.WeatherResponse
import retrofit2.http.DELETE

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_response")
    fun getAllOfflineData():  LiveData<List<WeatherResponse>>

    @Query("SELECT * FROM weather_response WHERE favourite LIKE 1")
    fun getFavouriteWeather():  LiveData<List<WeatherResponse>>

    @Query("SELECT * FROM weather_response WHERE favourite LIKE 0")
    fun getCurrentWeather():  LiveData<List<WeatherResponse>>

    @Insert
    fun insertAll(vararg weather: WeatherResponse)

    @Delete
    fun delete(weather: WeatherResponse)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeather(vararg weather: WeatherResponse)

    @Query("DELETE FROM weather_response WHERE favourite Like 1")
    fun deleteAllFavourite(vararg weather: WeatherResponse)

    @Query("DELETE FROM weather_response WHERE favourite Like 0")
    fun deleteAllCurrent(vararg weather: WeatherResponse)

    //dangerous command which will always be used :) nuke all data all over THE DATABASE LIKE A SMALL PIECE OF BUG CRASHED BY A PANZER FROM HELL ..
    @Query("DELETE FROM Weather_Response")
    fun clearALLOfflineData()
}