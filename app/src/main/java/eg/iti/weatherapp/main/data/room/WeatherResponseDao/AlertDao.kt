package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import androidx.room.*
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM alert")
    fun getAllAlerts(): Flow<List<AlertNotification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlert(alert: AlertNotification)

    @Delete
    fun deleteAlert(alert: AlertNotification)

}