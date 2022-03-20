package eg.iti.weatherapp.main.ui.alert

import android.content.Context
import android.content.SharedPreferences
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.ui.favourite.AlertView
import eg.iti.weatherapp.main.ui.favourite.FavouriteViewModel
import eg.iti.weatherapp.main.ui.location.toast
import eg.iti.weatherapp.main.utils.DateUtils
import eg.iti.weatherapp.main.utils.LocaleUtil
import java.util.*

class AlertAdapter(viewModel: AlertViewModel) : RecyclerView.Adapter<AlertAdapter.ViewHolder>() {
    var locations = mutableListOf<AlertNotification>()
    lateinit var context: Context
    private var viewModel: AlertViewModel = viewModel
    private lateinit  var sharedPreferences : SharedPreferences

    fun setAlertList(sp: List<AlertNotification>) {
        this.locations = sp.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: ConstraintLayout
        var deleteImage: ImageView
        var txtStartTime: TextView
        var txtStartDate: TextView
        var txtEndTime: TextView
        var txtEndDate: TextView
        init {
            card=itemView.findViewById(R.id.custom_row_alert_layout)
            txtStartTime=itemView.findViewById(R.id.custom_row_startTime)
            txtStartDate=itemView.findViewById(R.id.custom_row_startDate)
            txtEndTime=itemView.findViewById(R.id.custom_row_endTime)
            txtEndDate=itemView.findViewById(R.id.custom_row_endDate)

            deleteImage = itemView.findViewById(R.id.custom_row_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_alert, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        var alertDetails = locations[position]
        holder.deleteImage.setOnClickListener { viewModel.deleteData(alertDetails,context)  }  //delete from data base using location details
//        holder.card.setOnClickListener{
//            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
//            editor.putString(context.getString(R.string.preference_longitude), locationDetails.locationLongtuide)
//            editor.putString(context.getString(R.string.preference_alatitude), locationDetails.locationLatetuide)
//            editor.apply()
//            editor.commit()
//            context.toast(context.getString(R.string.city_picked))
//        }     //set those data to shared preferences and recreate activity
//        var timeZone = LocaleUtil.getCityName(lat =locationDetails.locationLatetuide.toDouble() ,lon =locationDetails.locationLongtuide.toDouble(),context)
//        if (timeZone == context.getString(eg.iti.weatherapp.R.string.def))
//            holder.cityName.text = locationDetails.locationCountryName
//        else
//            holder.cityName.text=timeZone

        holder.txtStartTime.text =DateUtils.convertAlertTime(alertDetails.startTime, Locale.getDefault())
        holder.txtStartDate.text =DateUtils.convertAlertDate(alertDetails.startTime, Locale.getDefault())

        holder.txtEndDate.text =DateUtils.convertAlertDate(alertDetails.endTime, Locale.getDefault())
        holder.txtEndTime.text =DateUtils.convertAlertTime(alertDetails.endTime, Locale.getDefault())
    }

    override fun getItemCount(): Int = locations.size

}
