package eg.iti.weatherapp.main.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.preference.PreferenceManager
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val currentWeather = MutableLiveData<WeatherResponse>()
    val errorMessage = MutableLiveData<String>()

    fun getCurrentWeather(context: Context) {
        val sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context)

//    Preference.DEFAULT_ORDER
        val response = mainRepository.getCurrentWeatherByLocation(
            lat = sharedpreferences.getString(
                context.getString(R.string.preference_alatitude),
                "33"
            )
                .toString(),
            lon = sharedpreferences.getString(
                context.getString(R.string.preference_longitude),
                "29"
            )
                .toString(),
            lang = sharedpreferences.getString(
                context.getString(R.string.preference_language),
                context.getString(R.string.pref_en)
            ).toString(),
            API = context.getString(R.string.weather_api)
        )
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                runBlocking {
                    mainRepository.clearAllWeatherResponseData(context)
                    mainRepository.insertWeatherResponseToDatabase(response.body()!!, context)
                    currentWeather.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                getOfflineStoredData(context)
            }
        })
    }

    fun getOfflineStoredData(context: Context): LiveData<List<WeatherResponse>> =
        mainRepository.getAllWeatherResponseFromDataBAse(context = context).asLiveData()

}