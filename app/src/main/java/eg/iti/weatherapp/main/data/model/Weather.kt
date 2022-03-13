package eg.iti.weatherapp.main.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather constructor(
    @SerializedName("id")
    val id: String,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String

) : Serializable