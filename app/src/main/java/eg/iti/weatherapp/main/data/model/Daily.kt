package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.Converters
import java.io.Serializable

//@Entity(tableName = "WeatherResponse" )
data class Daily(

    @SerializedName("dt")
    val dt : String,

    @TypeConverters(Converters::class)
    @SerializedName("temp")
    val temp : Temp,

    @TypeConverters(Converters::class)
    @SerializedName("weather")
    val weather: List<Weather>
    ): Serializable

