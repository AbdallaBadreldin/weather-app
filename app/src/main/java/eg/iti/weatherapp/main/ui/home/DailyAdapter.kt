package eg.iti.weatherapp.main.ui.home

import eg.iti.weatherapp.main.data.model.Daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.iti.weatherapp.databinding.CustomRowDailyBinding
import eg.iti.weatherapp.main.ui.home.DailyAdapter

class DailyAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var movies = mutableListOf<Daily>()

    fun setDailyList(movies: List<Daily>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = CustomRowDailyBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
//        val movie = movies[position]
//        holder.binding.name.text = movie.name
//        Glide.with(holder.itemView.context).load(movie.imageUrl).into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MainViewHolder(val binding: CustomRowDailyBinding) : RecyclerView.ViewHolder(binding.root) {

}
