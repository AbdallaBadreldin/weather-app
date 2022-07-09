package eg.iti.weatherapp.main.ui.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.ui.welcome.WelcomeFragment
import java.util.concurrent.TimeUnit

lateinit var viewModel: NotificationWorkerViewModel
var notificationId = 1
var CHANNEL_ID = 9510

lateinit var userReminders: List<AlertNotification>
lateinit var dataInsideDB: List<WeatherResponse>
lateinit var soon_alerts: List<AlertNotification>

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
//        viewModel = ViewModelProvider(
//            this, MyViewModelFactory(
//
//            )
//        ).get(NotificationWorkerViewModel::class.java)
        Log.v("WORKER","FROM WORKER")

        var repo = MainRepository(
            LocalSource(),
            RemoteSource()
        )


        createNotificationChannel()
//        createNotification()

        //first check data base for alarms if there's alarm with in next 12 hours run one time if not run periodic

        kotlin.run {
            userReminders = repo.getAllStoredUsersAlertsForWorkManager(applicationContext)

            dataInsideDB = repo.getAllStoredWeatherDataForWorkManager(applicationContext)

            soon_alerts = repo.getAlertsBetween(
                System.currentTimeMillis(),
                System.currentTimeMillis() + 900,
                context = applicationContext
            )
            Log.v("WORKER", soon_alerts.size.toString())

            Log.v("WORKER","FROM INSIDE RUN")
        }

        if (userReminders == null) {        //nodata in app    we can download new data here
            Log.v("WORKER","FROM INSIDE USERREMINDER")
//            startPeriodicWork()
            return Result.success()
        }

        if (userReminders.isNullOrEmpty()) {   //no user reminders
            Log.v("WORKER","FROM INSIDE USERREMINDER 2")

//            startPeriodicWork()
            return Result.success()
        }

//        if (dataInsideDB.isNullOrEmpty() ) {

//            createNotification(
//                applicationContext.getString(R.string.every_thing_is_ok),
//                applicationContext.getString(R.string.iti_weather_app)
//            )
//            WorkManager.getInstance(applicationContext).cancelAllWork()
//            startPeriodicWork()
//            return Result.success()
//        }



        if (soon_alerts.isNullOrEmpty() || soon_alerts.size == 1) {   //there is alerts
            Log.v("WORKER","FROM INSIDE SoON ALERTS   // ")
            if (dataInsideDB==null) //there's no data to fetch
            return Result.success()

            if(dataInsideDB[0].alerts==null)
                return Result.success()

            if(dataInsideDB[0].alerts.isNullOrEmpty()) {
                createNotification(
                    applicationContext.getString(R.string.every_thing_is_ok),
                    applicationContext.getString(R.string.iti_weather_app)
                )

                return Result.success()
            }     //I guess here calm Notification

            //next step is to call hard one time notification
                if(dataInsideDB.isNullOrEmpty()){
                    for (alert in dataInsideDB[0].alerts!!) {
                        if (alert!!.start <= System.currentTimeMillis() && System.currentTimeMillis() <= alert!!.end) {
                    Log.v("WORKER","FROM INSIDE SoON ALERTS   inside if statemnet ")
                    createNotification(
                        applicationContext.getString(R.string.every_thing_is_ok),
                        applicationContext.getString(R.string.iti_weather_app)
                    )
                        }}
                }
                else{
                    Log.v("WORKER","FROM INSIDE DATA NoT NULL")
                    for (alert in dataInsideDB[0].alerts!!) {
                        if (alert!!.start <= System.currentTimeMillis() && System.currentTimeMillis() <= alert!!.end) {
                            Log.v("WORKER","THERE ARE DATA IN BETWEEN WORK MANAGER")
                            createNotification(
                                alert.description,
                                applicationContext.getString(R.string.iti_weather_app)
                            )
                            return Result.success()
                        }
                }

                    //here we will check for onetime
                return Result.success()
            }

            return Result.success()
        }


        if (dataInsideDB[0].alerts!!.size >= 1) {
            for (alert in dataInsideDB[0].alerts!!) {
                if (alert!!.start <= System.currentTimeMillis() && System.currentTimeMillis() <= alert.end) {
                    createNotification(
                        alert.description,
                        applicationContext.getString(R.string.watch_out_disaster)
                    )
//                    startPeriodicWork()

                    return Result.success()
                }
                break

            }
            return Result.success()
        }


//        get from data base data in which between current and current + 15mins


//        else {
//            startOneTimeWorkWithIn(soon_alerts[0].startTime - System.currentTimeMillis() / 1000)
//            return Result.success()
//        }
//    startOneTimeWorkWithIn()
//        startPeriiodicWork()


        // else {
//                for (element in dataInsideDB) {
//                    for (alert in element.alerts) {
//                        if (alert.start < System.currentTimeMillis() && System.currentTimeMillis() < alert.end)
//                            createNotification(
//                                alert.description,
//                                applicationContext.getString(R.string.watch_out_disaster)
//                            )
//                        startPeriiodicWork()
//                    }
//
//                }

//                startOneTimeWorkWithIn(60)
//                return Result.success()
//            }
//        }


        WorkManager.getInstance(applicationContext).cancelAllWork()
        return Result.success()
    }



    //old version
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.channel_name)
            val descriptionText = applicationContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID.toString(), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(notificationContent: String, notificationTitle: String) {
        val notifyIntent = Intent(applicationContext, WelcomeFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
//        notifyIntent.putExtra(NOTIFICATION_EXTRA, true)
//        notifyIntent.putExtra(NOTIFICATION_ID, notificationId)
        val notifyPendingIntent = PendingIntent.getActivity(
            applicationContext, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat
            .Builder(applicationContext, CHANNEL_ID.toString())
            .setSmallIcon(R.drawable.eleven_d)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId++, builder.build())
        }
    }

    fun startPeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
        val work = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            "periodWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )


//        WorkManager.getInstance(applicationContext).enqueue()

    }

    fun startOneTimeWorkWithIn(timeInSeconds: Long, endPoint: String) {
        val workManager = WorkManager.getInstance(applicationContext.applicationContext!!)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(false)
            .build()
        val data = Data.Builder()
        data.putString("ENDPOINT_REQUEST", endPoint)
        val work = OneTimeWorkRequestBuilder<OneTimeNotificationWorker>()
            .setInitialDelay(timeInSeconds, java.util.concurrent.TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()
        workManager.enqueue(work)
    }

}
