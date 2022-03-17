package eg.iti.weatherapp.main.data.retrofit

import eg.iti.weatherapp.main.data.model.WeatherResponse
import retrofit2.Call

interface RemoteSourceInterface {
    fun getCurrent(lat:String,lon:String,unit:String,lang:String,API:String) : Call<WeatherResponse>
}