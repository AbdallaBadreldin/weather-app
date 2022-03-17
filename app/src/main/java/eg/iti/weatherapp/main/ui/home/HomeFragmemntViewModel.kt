package eg.iti.weatherapp.main.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmemntViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val currentWeather = MutableLiveData<WeatherResponse>()
    val errorMessage = MutableLiveData<String>()



//    alatitude : String , longtuide :String ,units :String , language :String , api :String
    fun getCurrentWeather(context: Context){
    val  sharedpreferences=  PreferenceManager.getDefaultSharedPreferences(context);
//    val  sharedpreferences  = context.getSharedPreferences(context.getString(R.string.preference_language), Context.MODE_PRIVATE);
////    &lang={lang}  ar - en
////    &unit={unit}  standard - metric - imperial
//        //wherever u want to get token
    val alatitude :String = sharedpreferences.getString(context.getString(R.string.preference_alatitude), "33").toString()
    val longtuide = sharedpreferences.getString(context.getString(R.string.preference_longitude), "29").toString()
//    Toast.makeText(context,longtuide,Toast.LENGTH_LONG).show()
    val language = sharedpreferences.getString(context.getString(R.string.preference_language), context.getString(R.string.pref_en)).toString()
    val api = context.getString(R.string.weather_api)


//    Preference.DEFAULT_ORDER
      val response = mainRepository.getCurrentWeatherByLocation(alatitude,longtuide, unit = "standard",language,api)
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                currentWeather.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}