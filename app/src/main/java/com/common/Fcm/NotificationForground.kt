package com.common.Fcm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.corona_ui.bluetoothlocator.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class NotificationForground : FirebaseMessagingService()
{
    val channelId = "01"
    val channel_Name="AppName"//getString(R.string.app_name)
    val channel_Desc="In App Notification for Alerts"//getString(R.string.app_name)+" Alert"

    override fun onMessageReceived(p0: RemoteMessage){
            println("Executed notification  .........22")
            if (p0.notification!=null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    var notificationChannel=NotificationChannel(channelId,channel_Name,NotificationManager.IMPORTANCE_DEFAULT)
                    notificationChannel.description=channel_Desc
                    var notificationManager=getSystemService(NotificationManager::class.java)
                    notificationManager?.createNotificationChannel(notificationChannel)
                }
                 displayNotification(p0.notification?.title!!,p0.notification?.body!!)
            }

        }

    fun displayNotification(contentTitle:String,contentText:String){
        val notificationCompat= NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.ic_notify)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var notificationManagerCompat= NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(1,notificationCompat.build())
    }

}