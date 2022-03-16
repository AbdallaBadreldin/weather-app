package eg.iti.weatherapp.main.ui.home

import eg.iti.weatherapp.main.data.model.Daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.iti.weatherapp.databinding.CustomRowDailyBinding
import eg.iti.weatherapp.main.data.model.Hourly
import eg.iti.weatherapp.main.ui.home.DailyAdapter

class DailyAdapter: RecyclerView.Adapter<MainViewHolder>() {

     var dailys : List<Daily> =mutableListOf<Daily>()

    fun setDailyList(dailys: List<Daily>) {
        this.dailys = dailys
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = CustomRowDailyBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val weatherDetail = dailys[position]
//        holder.binding.dailyDay
        holder.binding.dailyDec.text= weatherDetail.weather[0].description
        holder.binding.dailyDegFeel.text= "${weatherDetail.temp.max} / ${weatherDetail.temp.min}"
//        holder.binding.dailyImage
    }

    override fun getItemCount(): Int {
        if (dailys.isNullOrEmpty())            //watch out
            return 0
        return dailys.size
    }
}

class MainViewHolder(val binding: CustomRowDailyBinding) : RecyclerView.ViewHolder(binding.root)
