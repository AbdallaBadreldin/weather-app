package eg.iti.weatherapp.main.data.room.WeatherResponseDao

import android.content.Context
import androidx.room.*
import eg.iti.weatherapp.main.data.model.*

//@ProvidedTypeConverter

@Database(entities = [WeatherResponse::class], version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDataBase : RoomDatabase() {
        abstract fun weatherDao() : WeatherDao
    companion object {
        @Volatile
        private var instance: WeatherDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDataBase::class.java,
                "WeatherResponse",
            ).addTypeConverter(Converters())
                .fallbackToDestructiveMigration()
                .build()
    }
}
