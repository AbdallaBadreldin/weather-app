package eg.iti.weatherapp.main.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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

    @SerializedName("weather")
    val weather :List<Weather>

    ): Serializable