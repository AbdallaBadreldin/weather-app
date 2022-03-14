package eg.iti.weatherapp.main.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Temp(
    @SerializedName("max")
    val min :String,

    @SerializedName("min")
    val max :String
): Serializable
