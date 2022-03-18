package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import androidx.lifecycle.LiveData
import androidx.room.*
import eg.iti.weatherapp.main.data.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherResponse")
    fun getAllOfflineData(): Flow<List<WeatherResponse>>

    @Query("SELECT * FROM WeatherResponse WHERE favourite LIKE 1")
    fun getFavouriteWeather():  Flow<List<WeatherResponse>>

    @Query("SELECT * FROM WeatherResponse WHERE favourite LIKE 0")
    fun getCurrentWeather(): Flow<List<WeatherResponse>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeatherResponse(weather: WeatherResponse)

    @Delete
    fun delete(weather: WeatherResponse)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeather(vararg weather: WeatherResponse)

//    @Query("DELETE FROM WeatherResponse WHERE favourite Like 1")
//    fun deleteAllFavourite()
//
//    @Query("DELETE FROM WeatherResponse WHERE favourite Like 0")
//    fun deleteAllCurrent()

    //dangerous command which will always be used :) nuke all data all over THE DATABASE LIKE A SMALL PIECE OF BUG CRASHED BY A PANZER FROM HELL ..
    @Query("DELETE FROM WeatherResponse")
    fun clearALLOfflineData()
}