package eg.iti.weatherapp.main.data.repository.database.WeatherResponseDao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.database.Converters


@Database(entities = [WeatherResponse::class], version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDataBase : RoomDatabase(), WeatherDao {
    abstract fun weatehrDao(): WeatherDao

    companion object {
        @Volatile private var INSTANCE: WeatherDataBase? = null

        fun getInstance(context: Context): WeatherDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, WeatherDataBase::class.java, "Weather_Response.db").build()
    }
}