package eg.iti.weatherapp.main.ui.home

import android.content.Context
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.CustomRowDailyBinding
import eg.iti.weatherapp.databinding.CustomRowHourlyBinding
import eg.iti.weatherapp.main.data.model.Hourly

//  list: List<Hourly>,
class HourlyAdapter( ): RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    var hourlys = mutableListOf<Hourly>()


//    init {
//      this.hourlys = list.toTypedArray().toMutableList()
//    }
    fun setSportsList(sp: List<Hourly>) {
        this.hourlys = sp.toMutableList()
        notifyDataSetChanged()
    }
    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        lateinit var card: CardView
        var image: ImageView
        var time: TextView
        var temp: TextView

        init {
            time = itemView.findViewById(R.id.hourly_dt)
            temp = itemView.findViewById(R.id.hourly_temp_degree) /// need to convert it using utils to hours
            image = itemView.findViewById(R.id.hourly_image)

        }
    }

    fun setHourlyListItems(body: List<Hourly>)
    {
hourlys = body as MutableList<Hourly>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_hourly, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var hourlyDetails = hourlys[position]
holder.time.text =  hourlyDetails.dt    //convert it please
        holder.temp.text = hourlyDetails.temp
//        holder.image.setImageResource() // no photos yet
    }


    override fun getItemCount(): Int= hourlys.size

}
