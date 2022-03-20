package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eg.iti.weatherapp.main.data.model.*
import java.lang.reflect.Type

@ProvidedTypeConverter
class Converters {

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

    @TypeConverter
    fun fromCurrentToString(current: Current): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun fromStringToCurrent(current: String): Current {
        val listType: Type = object : TypeToken<Current?>() {}.type
        return Gson().fromJson(current, listType)
    }

    //============================Current Weather Response


    @TypeConverter
    fun fromCloudsToString(current: Clouds): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun fromStringToClouds(current: String): Clouds {
        val listType: Type = object : TypeToken<Clouds?>() {}.type
        return Gson().fromJson(current, listType)
    }

    @TypeConverter
    fun fromCoordToString(current: Coord): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun fromStringToCoord(current: String): Coord {
        val listType: Type = object : TypeToken<Coord?>() {}.type
        return Gson().fromJson(current, listType)
    }

    @TypeConverter
    fun fromSysToString(current: Sys): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun fromStringToSys(current: String): Sys {
        val listType: Type = object : TypeToken<Sys?>() {}.type
        return Gson().fromJson(current, listType)
    }

    @TypeConverter
    fun fromWindToString(current: Wind): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun fromStringToWind(current: String): Wind {
        val listType: Type = object : TypeToken<Wind?>() {}.type
        return Gson().fromJson(current, listType)
    }

    @TypeConverter
    fun fromAlertToString(current: Alert): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun fromStringToAlert(current: String): List<Alert?> {
        val listType: Type = object : TypeToken<List<Alert?>?>() {}.type
        return Gson().fromJson(current, listType)
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
