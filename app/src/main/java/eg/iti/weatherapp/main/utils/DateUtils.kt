package eg.iti.weatherapp.main.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils constructor(var time : Long , locale: Locale)  {
     var simpleDateFormat:SimpleDateFormat

init {
      simpleDateFormat=SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", locale)
}
   companion object fun convertDate(): String {

        return getDateString(time)
    }

    private fun getDateString(time: Long) : String = simpleDateFormat.format(time * 1000L)

//    private fun getDateString(time: Int) : String = simpleDateFormat.format(time * 1000L) //just for test

}