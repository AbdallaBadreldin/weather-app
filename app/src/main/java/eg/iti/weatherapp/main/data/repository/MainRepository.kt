package eg.iti.weatherapp.main.data.repository

import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource

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