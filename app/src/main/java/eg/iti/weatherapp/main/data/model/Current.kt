package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.Converters
import java.io.Serializable

//@Entity(tableName = "WeatherResponse" )
data class Current constructor(
    @SerializedName("dt")
    val dayTime :String,

    @SerializedName("temp")
    val temp :String,

    @SerializedName("pressure")
    val pressure :String,

    @SerializedName("humidity")
    val humidity :String,

    @SerializedName("uvi")
    val ultraViolet :String,

    @SerializedName("visibility")
    val visibility :String,

    @SerializedName("wind_speed")
    val windSpeed :String,

    @SerializedName("clouds")
    val cloud :String,

    @TypeConverters(Converters::class)
    @SerializedName("weather")
    val weather :List<Weather>

    ): Serializable