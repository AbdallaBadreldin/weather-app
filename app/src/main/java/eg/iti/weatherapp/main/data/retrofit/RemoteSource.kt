package eg.iti.weatherapp.main.data.retrofit

import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.retrofit.RetrofitClient.retrofitService
import retrofit2.Call

class RemoteSource {

fun getCurrent(lat:String,lon:String,unit:String,lang:String,API:String): Call<WeatherResponse> {
    return retrofitService.getCurrentByLocation(lat,lon,unit,lang,API)
}

}