package eg.iti.weatherapp.main.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.Converters
import java.io.Serializable

@Entity(tableName = "WeatherResponse", primaryKeys =  ["lat","lon"] )
data class WeatherResponse constructor(

    @SerializedName("lat")
    @ColumnInfo(name = "lat")
    val lat: String,

    @SerializedName("lon")
    @ColumnInfo(name = "lon")
    val lon: String,

    @ColumnInfo(name = "timezone")
    @SerializedName("timezone")
    val timeZone: String,

    @TypeConverters(Converters::class)
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

//    @TypeConverters(Converters::class)
//    @ColumnInfo(name = "favourite")
//    val favourite:Boolean = false

) : Serializable