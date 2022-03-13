package eg.iti.weatherapp.main.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherResponse constructor(

    @SerializedName("timezone")
    val timeZone :String,

    @SerializedName("current")
    val current :Current

):Serializable {
}