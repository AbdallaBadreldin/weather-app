package eg.iti.weatherapp.main.ui.alert

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.ui.base.MyViewModelFactory
import eg.iti.weatherapp.main.ui.location.toast
import eg.iti.weatherapp.main.ui.workmanager.NotificationWorker
import eg.iti.weatherapp.main.ui.workmanager.OneTimeNotificationWorker
import eg.iti.weatherapp.main.utils.DateUtils
import java.sql.Timestamp
import java.util.*
import kotlin.math.log


class AlertDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var fromBtn: Button
    lateinit var toBtn: Button
    lateinit var saveBtn: Button
    lateinit var radioGroup: ViewGroup

    lateinit var alarmButton: RadioButton
    lateinit var notificationButton: RadioButton

    lateinit var startDate: TextView
    lateinit var endDate: TextView
    lateinit var startTime: TextView
    lateinit var endTime: TextView

    companion object {
        fun newInstance(s: String) = AlertDialogFragment()
    }

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    var isEnd: Boolean = false

    var currentTimeInTimestamp: Long = 0

    lateinit var viewModel: AlertViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_alert_dialog, container, false)

        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                MainRepository(
                    LocalSource(),
                    RemoteSource()
                )
            )
        ).get(AlertViewModel::class.java)

        fromBtn = view.findViewById(R.id.alert_dialog_from)
        toBtn = view.findViewById(R.id.alert_dialog_to)
        saveBtn = view.findViewById(R.id.alert_dialog_saveBtn)
        radioGroup = view.findViewById(R.id.alert_dialog_radioGroup)

        alarmButton = view.findViewById(R.id.alert_dialog_alarmRadio)
        notificationButton = view.findViewById(R.id.alert_dialog_notificationRadio)

        startDate = view.findViewById(R.id.alert_dialog_startDate)
        startTime = view.findViewById(R.id.alert_dialog_startTime)

        endDate = view.findViewById(R.id.alert_dialog_endDate)
        endTime = view.findViewById(R.id.alert_dialog_endTime)

        alarmButton.isChecked = true

        fromBtn.setOnClickListener {
            isEnd = false
            openPicker()
        }
        toBtn.setOnClickListener {
            isEnd = true
            openPicker()
        }

        setTimeToText(pickedTimeInString)
        currentTimeInTimestamp = System.currentTimeMillis() / 1000
        startTime.text = DateUtils.convertAlertTime(currentTimeInTimestamp, Locale.getDefault())
        startDate.text = DateUtils.convertAlertDate(currentTimeInTimestamp, Locale.getDefault())
        endTime.text = DateUtils.convertAlertTime(currentTimeInTimestamp, Locale.getDefault())
        endDate.text = DateUtils.convertAlertDate(currentTimeInTimestamp, Locale.getDefault())
        start = currentTimeInTimestamp
        end = currentTimeInTimestamp



        saveBtn.setOnClickListener {

            if (start <= end) {
                viewModel.insertData(AlertNotification(0, start, end, true), requireContext())
//                WorkManager.getInstance(requireContext()).cancelAllWork()

                Log.v("WORKER","TIMES")
                Log.v("WORKER",start.toString())
                Log.v("WORKER",(System.currentTimeMillis()/1000).toString())

                startWorkManager(  System.currentTimeMillis()/1000- start     )
                firstTime=true
                dismiss()
            } else
                context?.toast(getString(R.string.end_time_cannot_be_before_start))
        }

        return view
    }

    private fun openPicker() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        val datePickerDialog =
            DatePickerDialog(requireContext(), this@AlertDialogFragment, year, month, day)
        datePickerDialog.show()
    }

    fun startWorkManager(delayTime:Long) {
        Log.v("WORKER",delayTime.toString())
        val workManager = WorkManager.getInstance(activity?.applicationContext!!)
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(false)
            .setRequiresStorageNotLow(false)
            .build()
//    val data = Data.Builder()
//        data.putString(ENDPOINT_REQUEST, endPoint)
        val work = OneTimeWorkRequestBuilder<OneTimeNotificationWorker>()
            .setInitialDelay(delayTime, java.util.concurrent.TimeUnit.SECONDS)
            .setConstraints(constraints)
//        .setInputData(data.build())
            .build()
        workManager.enqueue(work)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog =
            TimePickerDialog(requireContext(), this@AlertDialogFragment, hour, minute, false)
        timePickerDialog.show()
    }

    var pickedTimeInString: Long = 0

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        pickedTimeInString =
            Timestamp(year - 1900, myMonth, myDay, myHour, myMinute, 0, 0).time /1000

        setTimeToText(pickedTimeInString)
    }

    var start: Long = 0
    var end: Long = 0
    var firstTime:Boolean = true
    private fun setTimeToText(pickedTimeInString: Long) {    //1647   729045

        if (!firstTime && !isEnd && pickedTimeInString >= currentTimeInTimestamp)
        {  context?.toast(getString(R.string.cannot_choose_past))
        firstTime=false}
        else {
            if (isEnd) {
                end = this.pickedTimeInString
                endTime.text = DateUtils.convertAlertTime(end, Locale.getDefault())
                endDate.text = DateUtils.convertAlertDate(end, Locale.getDefault())
            }
            if (!isEnd) {
                start = this.pickedTimeInString
                startTime.text = DateUtils.convertAlertTime(start, Locale.getDefault())
                startDate.text = DateUtils.convertAlertDate(start, Locale.getDefault())
            }
        }

    }


}