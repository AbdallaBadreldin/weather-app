package eg.iti.weatherapp.main.data.retrofit

import eg.iti.weatherapp.main.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

//    &lang={lang}  ar - en
//    &unit={unit}  standard - metric - imperial

//    Temperature. Units - default: kelvin, metric: Celsius, imperial: Fahrenheit.

//    exclude=hourly,daily ,minutly
    @GET("onecall?exclude=minutely")
    fun getCurrentByLocation(@Query("lat")lat:String, @Query("lon")lon:String,  @Query("lang")lang:String, @Query("appid")API :String) : Call<WeatherResponse>


//    @GET("movielist.json")
//    fun getAllMovies() : Call<List<Movie>>

//@GET("movielist.json")
//fun getCurrentByLocation() : Call<List<Movie>>


//@GET("movielist.json")
//fun getCurrentBycity() : Call<List<Movie>>


}