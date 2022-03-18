package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import androidx.room.TypeConverters
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.Converters
import java.io.Serializable

@Entity(tableName = "CurrentResponse", primaryKeys =  ["lat","lon"] )
data class CurrentResponse(
    val base: String,
    @TypeConverters(Converters::class)
    val clouds: Clouds,
    val cod: Int,
    @TypeConverters(Converters::class)
    val coord: Coord,
    val dt: Int,
    val id: Int,
    @TypeConverters(Converters::class)
    val main: Main,
    val name: String,
    @TypeConverters(Converters::class)
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    @TypeConverters(Converters::class)
    val weather: List<Weather>,
    @TypeConverters(Converters::class)
    val wind: Wind
):Serializable