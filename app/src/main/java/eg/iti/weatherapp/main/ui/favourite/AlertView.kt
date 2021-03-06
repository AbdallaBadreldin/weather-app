package eg.iti.weatherapp.main.ui.favourite

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.ui.location.toast
import eg.iti.weatherapp.main.utils.LocaleUtil

class AlertView(viewModel: FavouriteViewModel) :RecyclerView.Adapter<AlertView.ViewHolder>() {
    var locations = mutableListOf<Location>()
    lateinit var context: Context
    private var viewModel: FavouriteViewModel
    private lateinit  var sharedPreferences : SharedPreferences

    init {
        this.viewModel=viewModel
    }
    fun setFavouriteList(sp: List<Location>) {
        this.locations = sp.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: CardView
        var deleteImage: ImageView
        var cityName: TextView

        init {
            card=itemView.findViewById(R.id.fav_row_card)
            cityName = itemView.findViewById(R.id.fav_row_text)
            deleteImage = itemView.findViewById(R.id.fav_row_delete)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_favourite_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        var locationDetails = locations[position]
        holder.deleteImage.setOnClickListener { viewModel.deleteLocation(locationDetails,context)  }  //delete from data base using location details
        holder.card.setOnClickListener{
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString(context.getString(R.string.preference_longitude), locationDetails.locationLongtuide)
            editor.putString(context.getString(R.string.preference_alatitude), locationDetails.locationLatetuide)
            editor.apply()
            editor.commit()
            context.toast(context.getString(R.string.city_picked))
        }     //set those data to shared preferences and recreate activity
        var timeZone = LocaleUtil.getCityName(lat =locationDetails.locationLatetuide.toDouble() ,lon =locationDetails.locationLongtuide.toDouble(),context)
        if (timeZone == context.getString(eg.iti.weatherapp.R.string.def))
           holder.cityName.text = locationDetails.locationCountryName
        else
        holder.cityName.text=timeZone

    }

    override fun getItemCount(): Int = locations.size

}
