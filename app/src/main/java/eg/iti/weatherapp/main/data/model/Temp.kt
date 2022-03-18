package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Entity(tableName = "WeatherResponse")
data class Temp(
    @SerializedName("max")
    val min :String,

    @SerializedName("min")
    val max :String
): Serializable
