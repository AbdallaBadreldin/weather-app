package eg.iti.weatherapp.main.ui.alert

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.Location
import eg.iti.weatherapp.main.data.repository.MainRepository

class AlertViewModel(val repository: MainRepository) : ViewModel() {

    fun getAllData(context : Context) : LiveData<List<AlertNotification>>
            =repository.getAllAlertsFromDataBAse(context = context).asLiveData()

    fun insertData(alert: AlertNotification, context : Context) {
        repository.insertAlertToDatabase(alert,context)
    }
    fun deleteData(alert: AlertNotification, context : Context) {
        repository.deleteAlert(alert, context)
    }
}