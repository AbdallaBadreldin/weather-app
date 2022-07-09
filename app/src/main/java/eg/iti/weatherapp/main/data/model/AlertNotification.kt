package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "AlertNotification" )
data class AlertNotification(
    @PrimaryKey(autoGenerate = true)var id:Int,
    var startTime : Long,
    var endTime : Long ,
    var active :Boolean):Serializable
