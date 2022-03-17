package eg.iti.weatherapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDao
import eg.iti.weatherapp.main.data.room.WeatherResponseDao.WeatherDataBase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException



@RunWith(AndroidJUnit4::class)
class EnityReadWriteTest {
    private lateinit var todoDao: WeatherDao
    private lateinit var db: WeatherDataBase

    @Before
    fun createDb() {
//        val context = InstrumentationRegistry.getContext()
//        db = Room.inMemoryDatabaseBuilder(
//            context, WeatherDataBase::class.java).build()
//        todoDao = db.weatehrDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
//        val todo: WeatherResponse = WeatherResponse("title", Current("d","d","f0","fd","df","df","dfs","Sfd",
//            listOf(Weather("id","main","Desc"))
//        )
//
//        dao.insertAll(  )
//        val todoItem = todoDao.get()
//        assertThat(todoItem, equalTo())
    }

    private data class Weather(val id: String,val main: String,val description: String) {

    }
}