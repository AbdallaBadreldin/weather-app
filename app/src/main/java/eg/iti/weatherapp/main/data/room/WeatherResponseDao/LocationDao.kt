package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import androidx.room.*
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getAllLocation(): Flow<List<Location>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

    //dangerous command which will not be used ;) nuke all data all over THE DATABASE LIKE A SMALL PIECE OF BUG CRASHED BY A PANZER FROM HELL ..
//    @Query("DELETE FROM location")
//    fun clearAllLocation()


}