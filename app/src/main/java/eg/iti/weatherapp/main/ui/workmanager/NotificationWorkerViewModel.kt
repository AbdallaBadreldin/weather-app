package eg.iti.weatherapp.main.ui.workmanager

import android.content.Context
import androidx.lifecycle.ViewModel
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class NotificationWorkerViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getUserReminders(context: Context): Flow<List<AlertNotification>> {
        return mainRepository.getAllAlertsFromDataBAse(context)
    }

    fun getStoredData(context: Context): Flow<List<WeatherResponse>> {
       return mainRepository.getAllWeatherResponseFromDataBAse(context)
    }
}