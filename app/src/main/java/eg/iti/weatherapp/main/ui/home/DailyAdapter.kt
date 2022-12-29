package eg.iti.weatherapp.main.ui.home

import android.content.Context
import eg.iti.weatherapp.main.data.model.Daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.CustomRowDailyBinding
import eg.iti.weatherapp.main.utils.LocaleUtil
import java.math.MathContext

class DailyAdapter: RecyclerView.Adapter<MainViewHolder>() {

     var dailys : List<Daily> =mutableListOf<Daily>()

    fun setDailyList(dailys: List<Daily>) {
        this.dailys = dailys
//        notifyDataSetChanged()
    }
lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = CustomRowDailyBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val weatherDetail = dailys[position]

        holder.binding.dailyDay.text = pickDay(position)
        holder.binding.dailyDec.text= weatherDetail.weather[0].description
        holder.binding.dailyDegFeel.text= "${LocaleUtil.translateEnglishToArabic(convertTempAsSharedPreferences(weatherDetail.temp.max),context)} / ${LocaleUtil.translateEnglishToArabic(convertTempAsSharedPreferences(weatherDetail.temp.min),context)}"
        holder.binding.dailyImage.setImageResource(pickPhoto(weatherDetail.weather[0].icon))
    }
    fun  convertTempAsSharedPreferences(temp :String ) :String{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val tempo =sharedPreferences.getString(context.getString(eg.iti.weatherapp.R.string.preference_temperature),context.getString(eg.iti.weatherapp.R.string.pref_kelvin))
        when(tempo){
            context.getString(eg.iti.weatherapp.R.string.pref_kelvin) -> return temp + context.getString(eg.iti.weatherapp.R.string.unit_kelvin)
            context.getString(eg.iti.weatherapp.R.string.pref_celsius) -> return (temp.toDouble() -273.15).toBigDecimal(
                MathContext(2) ).toString() + context.getString(eg.iti.weatherapp.R.string.unit_celsuis) //°C
            context.getString(eg.iti.weatherapp.R.string.pref_fahrenheit) -> return ((temp.toDouble() * 9/5 - 459.67).toBigDecimal(
                MathContext(2) )).toString() + context.getString(eg.iti.weatherapp.R.string.unit_fahrenhiet)
        }
        return temp
    }

    fun pickDay(dayNum :Int): String {
        when(dayNum){
            0 -> return context.getString(eg.iti.weatherapp.R.string.saturday)
            1 -> return context.getString(eg.iti.weatherapp.R.string.sunday)
            2 -> return context.getString(eg.iti.weatherapp.R.string.monday)
            3 -> return context.getString(eg.iti.weatherapp.R.string.tuesday)
            4 -> return context.getString(eg.iti.weatherapp.R.string.wednesday)
            5 -> return context.getString(eg.iti.weatherapp.R.string.thursday)
            6 -> return context.getString(eg.iti.weatherapp.R.string.friday)
            else -> return context.getString(eg.iti.weatherapp.R.string.saturday)
        }
    }

    fun pickPhoto(image :String): Int {
        when (image) {
            "01d" -> return R.drawable.oned
            "01n" -> return R.drawable.onen
            "02d" -> return R.drawable.twod
            "02n" -> return R.drawable.twon
            "03d" -> return R.drawable.threed
            "03n" -> return R.drawable.threen
            "04d" -> return R.drawable.fourd
            "04n" -> return R.drawable.fourn
            "09d" -> return R.drawable.nined
            "09n" -> return R.drawable.ninen
            "10d" -> return R.drawable.tend
            "10n" -> return R.drawable.tenn
            "11d" -> return R.drawable.eleven_d
            "11n" -> return R.drawable.eleven_n
            "13d" -> return R.drawable.thirteen_d
            "13n" -> return R.drawable.thirteen_n
            "50d" -> return R.drawable.fifty_d
            "50n" -> return R.drawable.fifty_n
           else ->  return R.drawable.oned
        }
    }
    override fun getItemCount(): Int {
        if (dailys.isNullOrEmpty())            //watch out
            return 0
        return dailys.size
    }
}


class MainViewHolder(val binding: CustomRowDailyBinding) : RecyclerView.ViewHolder(binding.root)
