package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import androidx.room.*
import eg.iti.weatherapp.main.data.model.AlertNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM AlertNotification")
    fun getAllAlerts(): Flow<List<AlertNotification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlert(alert: AlertNotification)

    @Delete
    fun deleteAlert(alert: AlertNotification)

    //WorkMAnager staff
    @Query("SELECT * FROM AlertNotification")
    fun getAllAlertsForWorkManager(): List<AlertNotification>

    @Query("SELECT * FROM AlertNotification WHERE startTime>= :start AND startTime <=:end ORDER BY startTime ASC   ")
    fun getAlertsBetweenForWorkManager(start: Long, end: Long): List<AlertNotification>
}