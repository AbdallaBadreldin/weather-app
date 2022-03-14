package eg.iti.weatherapp.main.data.repository.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eg.iti.weatherapp.main.data.model.Daily
import eg.iti.weatherapp.main.data.model.Hourly
import eg.iti.weatherapp.main.data.model.Weather
import java.lang.reflect.Type


class Converters {
companion object{
    @TypeConverter
    fun fromWeatherToString(weathers: List<Weather>): String {
        return Gson().toJson(weathers)
    }
    @TypeConverter
    fun fromStringToWeather(weathers: String): List<Weather> {
        val listType: Type = object : TypeToken<List<Weather?>?>() {}.type
        return Gson().fromJson(weathers, listType)
    }

    @TypeConverter
    fun fromHourlyToString(hourly: List<Hourly>): String {
        return Gson().toJson(hourly)
    }
    @TypeConverter
    fun fromStringToHourly(hourly: String): List<Hourly> {
        val listType: Type = object : TypeToken<List<Hourly?>?>() {}.type
        return Gson().fromJson(hourly, listType)
    }

    @TypeConverter
    fun fromDailyToString(dailys: List<Daily>): String {
        return Gson().toJson(dailys)
    }
    @TypeConverter
    fun fromStringToDaily(daily: String): List<Daily> {
        val listType: Type = object : TypeToken<List<Daily?>?>() {}.type
        return Gson().fromJson(daily, listType)
    }

}

}
//    companion object {
//        @TypeConverter
//        @JvmStatic
//        fun fromInstant(value: Instant): Long {
//            return value.toEpochMilli()
//        }
//
//        @TypeConverter
//        @JvmStatic
//        fun toInstant(value: Long): Instant {
//            return Instant.ofEpochMilli(value)
//        }
//    }
//}
//    @TypeConverter
//    public static String fromArrayListToString(ArrayList<String> days_of_week){
//        return new Gson().toJson(days_of_week);
//    }
//    @TypeConverter
//    public static ArrayList<String> fromStringToArrayList(String strDay){
//        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
//        return new Gson().fromJson(strDay,listType);
//    }

//}
