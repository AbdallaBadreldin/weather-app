package eg.iti.weatherapp.main.data.model

import androidx.room.Entity
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

@Entity(tableName = "location" , primaryKeys = ["locationLatetuide","locationLongtuide"] )
data class Location(val locationLatetuide:String ,val locationLongtuide:String , val locationCountryName :String):Serializable
