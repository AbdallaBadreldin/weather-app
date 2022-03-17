package eg.iti.weatherapp.main.data.model

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import eg.iti.weatherapp.main.data.room.Converters
import java.io.Serializable

data class Daily(

    @SerializedName("dt")
    val dt : String,

    @SerializedName("temp")
    val temp : Temp,

    @TypeConverters(Converters::class)
    @SerializedName("weather")
    val weather: List<Weather>
    ): Serializable

