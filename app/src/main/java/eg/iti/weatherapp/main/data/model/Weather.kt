package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Entity(tableName = "WeatherResponse")
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