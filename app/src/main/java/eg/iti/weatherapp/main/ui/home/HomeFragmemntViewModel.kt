package eg.iti.weatherapp.main.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmemntViewModel(private val mainRepository: MainRepository) : ViewModel() {
    // TODO: Implement the ViewModel to revoke liveupdates directly into home fragment
    val currentWeather = MutableLiveData<WeatherResponse>()
    val errorMessage = MutableLiveData<String>()

    fun getCurrentWeather(){
//    &lang={lang}  ar - en
//    &unit={unit}  standard - metric - imperial

      val response = mainRepository.getCurrentWeatherByLocation("33.44","-94.04","metric","en","6bfac4a32ea2220f97cade47d4ac244b")
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