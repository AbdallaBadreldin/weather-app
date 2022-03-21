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
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.data.model.AlertNotification
import eg.iti.weatherapp.main.data.model.WeatherResponse
import eg.iti.weatherapp.main.data.repository.MainRepository
import eg.iti.weatherapp.main.data.retrofit.RemoteSource
import eg.iti.weatherapp.main.data.room.LocalSource
import eg.iti.weatherapp.main.ui.welcome.WelcomeFragment



class OneTimeNotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        var repo = MainRepository(
            LocalSource(),
            RemoteSource()
        )

        createNotificationChannel()

        run {

            userReminders = repo.getAllStoredUsersAlertsForWorkManager(applicationContext)

            dataInsideDB = repo.getAllStoredWeatherDataForWorkManager(applicationContext)

            soon_alerts = repo.getAlertsBetween(
                System.currentTimeMillis(),
                System.currentTimeMillis() + 900,
                context = applicationContext
            )


        if (dataInsideDB == null) {
            createNotification(
                applicationContext.getString(R.string.every_thing_is_ok),
                applicationContext.getString(R.string.iti_weather_app)
            )
            return Result.success()
        }

        if (dataInsideDB.isNullOrEmpty()) {
            createNotification(
                applicationContext.getString(R.string.every_thing_is_ok),
                applicationContext.getString(R.string.iti_weather_app)
            )
            return Result.success()
        }


        if (dataInsideDB[0].alerts == null) {
            createNotification(
                applicationContext.getString(R.string.every_thing_is_ok),
                applicationContext.getString(R.string.iti_weather_app)
            )
            return Result.success()
        }

        if (dataInsideDB.size == 0) {
            createNotification(
                applicationContext.getString(R.string.every_thing_is_ok),
                applicationContext.getString(R.string.iti_weather_app)
            )
            return Result.success()
        }

        if (dataInsideDB[0].alerts.size == 0) {
            createNotification(
                applicationContext.getString(R.string.every_thing_is_ok),
                applicationContext.getString(R.string.iti_weather_app)
            )
            return Result.success()
        }

        if (dataInsideDB[0].alerts.isNullOrEmpty()) {

            createNotification(
                dataInsideDB[0].alerts[0].description,
                applicationContext.getString(R.string.iti_weather_app)
            )
            return Result.success()

        }

    }
//Log.v("WORKER","ONE TIME WORKER")

//        WorkManager.getInstance(applicationContext).cancelAllWork()
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
}