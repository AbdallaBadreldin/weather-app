package eg.iti.weatherapp.main.data.model

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.Converters

data class Alerts(
    val description: String,
    val end: Long,
    val event: String,
    val sender_name: String,
    val start: Long,
    @TypeConverters(Converters::class)
    val tags: List<String>
)