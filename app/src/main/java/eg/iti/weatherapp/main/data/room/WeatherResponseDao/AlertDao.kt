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

    //workmanager staff
    @Query("SELECT * FROM alert")
    fun getAllAlertsForWorkManager() : List<AlertNotification>

    @Query("SELECT * FROM alert WHERE startTime>= :start AND startTime <=:end ORDER BY startTime ASC   ")
    fun getAlertsBetweenForWorkManager(start:Long ,end:Long) : List<AlertNotification>
}