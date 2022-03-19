package eg.iti.weatherapp.main.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.Hourly
import eg.iti.weatherapp.main.utils.DateUtils
import eg.iti.weatherapp.main.utils.LocaleUtil
import java.math.MathContext
import java.util.*

//  list: List<Hourly>,
class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    var hourlys = mutableListOf<Hourly>()
    lateinit var context: Context


    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        lateinit var card: CardView
        var image: ImageView
        var time: TextView
        var temp: TextView

        init {
            time = itemView.findViewById(R.id.hourly_dt)
            temp =
                itemView.findViewById(R.id.hourly_temp_degree) /// need to convert it using utils to hours
            image = itemView.findViewById(R.id.hourly_image)

        }
    }

    fun setHourlyListItems(body: List<Hourly>) {
        hourlys = body as MutableList<Hourly>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_hourly, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var hourlyDetails = hourlys[position]
        holder.time.text =
            DateUtils.convertHour(hourlyDetails.dt.toLong(), Locale.getDefault()).toString()
        holder.temp.text = LocaleUtil.translateEnglishToArabic(convertTempAsSharedPreferences(hourlyDetails.temp), context)
        holder.image.setImageResource(pickPhoto(hourlyDetails.weather[0].icon)) // no photos yet
    }

    fun pickPhoto(image: String): Int {
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
            else -> return R.drawable.twon
        }
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

    override fun getItemCount(): Int = hourlys.size

}
