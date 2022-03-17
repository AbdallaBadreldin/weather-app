package eg.iti.weatherapp.main.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import eg.iti.weatherapp.main.data.room.Converters
import java.io.Serializable

@Entity(tableName = "Weather_Response")
data class WeatherResponse constructor(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("lat")
    val lat: String,

    @PrimaryKey(autoGenerate = false)
    @SerializedName("lon")
    val lon: String,

    @PrimaryKey(autoGenerate = false)
    @SerializedName("timezone")
    val timeZone: String,

    @ColumnInfo(name = "current")
    @SerializedName("current")
    val current: Current,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "daily")
    @SerializedName("daily")
    val daily: List<Daily>,


    @TypeConverters(Converters::class)
    @ColumnInfo(name = "hourly")
    @SerializedName("hourly")
    val hourly: List<Hourly>,

    var favourite:Boolean = false
) : Serializable