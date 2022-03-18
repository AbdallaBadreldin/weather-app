package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.Converters
import java.io.Serializable

//@Entity(tableName = "WeatherResponse" )
data class Hourly(
    @SerializedName("dt")
    val dt : String,

    @SerializedName("temp")
    val temp : String,

    @SerializedName("feels_like")
    val feels_like :String,

    @TypeConverters(Converters::class)
    @SerializedName("weather")
    val weather: List<Weather>
): Serializable
