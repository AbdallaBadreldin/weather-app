package eg.iti.weatherapp.main.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils constructor( ) {


    companion object {


        fun convertDate(time: Long, locale: Locale): String {
            var simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", locale)
            return getDateString(time,simpleDateFormat)
        }
        fun convertHour(time: Long , locale: Locale):String{
            var simpleDateFormatForDay = SimpleDateFormat("hh aa", locale)
            return  getHourString(time,simpleDateFormatForDay)
        }

        private fun getDateString(time: Long, simpleDateFormat:SimpleDateFormat): String = simpleDateFormat.format(time * 1000L)

        private fun getHourString(time: Long, simpleDateFormat:SimpleDateFormat): String = simpleDateFormat.format(time * 1000L)

    }


}