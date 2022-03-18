package eg.iti.weatherapp.main.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.bumptech.glide.load.engine.Resource
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.acl.Owner

class HomeFragmemntViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val currentWeather = MutableLiveData<WeatherResponse>()
    val errorMessage = MutableLiveData<String>()

//    private val _weather = _id.switchMap { id ->
//        mainRepository.getCurrentWeatherFromDataBase(id )
//    }
//    val character: LiveData<Resource<WeatherResponse>> = _weather

    // Create a LiveData with a String
//    val currentName: MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }


    //    alatitude : String , longtuide :String ,units :String , language :String , api :String
    fun getCurrentWeather(context: Context) {
        val sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context)

////    &unit={unit}  standard - metric - imperial
//        //wherever u want to get token
        val alatitude: String =
            sharedpreferences.getString(context.getString(R.string.preference_alatitude), "1")
                .toString()
        val longtuide =
            sharedpreferences.getString(context.getString(R.string.preference_longitude), "1")
                .toString()

        val language = sharedpreferences.getString(
            context.getString(R.string.preference_language),
            context.getString(R.string.pref_en)
        ).toString()
        val api = context.getString(R.string.weather_api)


//    Preference.DEFAULT_ORDER
        val response = mainRepository.getCurrentWeatherByLocation(
            alatitude,
            longtuide,
            unit = "standard",
            language,
            api
        )
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                mainRepository.deleteCurrentWeather(context)
                mainRepository.insertWeatherResponseToDatabase(response.body()!!, context)
                currentWeather.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                errorMessage.postValue(t.message)

            }
        })
    }

    fun getOfflineStoredData(context : Context) : LiveData<List<WeatherResponse>>
    =mainRepository.getCurrentWeatherFromDataBase(context = context).asLiveData()
//        var temp:List<WeatherResponse>

//             mainRepository.getCurrentWeatherFromDataBase(context.requireContext()).
//             observe(  context as HomeFragmemnt,

//        if(temp.isNullOrEmpty())//error
//
//        else
//        mainRepository.getCurrentWeatherFromDataBase(context.requireContext() ).observe(owner, Observer<List<WeatherResponse?>?> {
//            var tempo =context.childFragmentManager.fragments[0] as HomeFragmemnt
//            tempo.setDataIntoLayout( it[0] )

//    ) //get all data




}