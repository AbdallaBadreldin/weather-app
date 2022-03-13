package eg.iti.weatherapp.main.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.database.LocalSource
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.retrofit.RetrofitClient
import eg.iti.weatherapp.main.data.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository (private val localSource : LocalSource, private val remoteSource : RemoteSource){

fun getCurrentWeatherByLocation(lat:String,lon:String,unit:String,lang:String,API:String)
= remoteSource.getCurrent(lat,lon,unit,lang,API)



    /*fun getCurrentWeatherLiveDataByLocation(context: Context) : MutableLiveData<WeatherResponse> {

        val mutableLiveData = MutableLiveData<WeatherResponse>()

//        context.showProgressBar()

        remoteSource.getCurrent("33.44","33.44","standard","en",context.getString(R.string.weather_api)).enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//                hideProgressBar()
                Log.e("error", t.localizedMessage as String)
            }

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
//                hideProgressBar()
                val usersResponse = response.body()
                usersResponse?.let { mutableLiveData.value = it as WeatherResponse }
            }

        })

        return mutableLiveData
    }*/
}