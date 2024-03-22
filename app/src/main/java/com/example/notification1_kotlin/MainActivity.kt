package com.example.notification1_kotlin

import android.annotation.SuppressLint
import android.app.Instrumentation.ActivityResult
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat as MediaNotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var notificationId = 0;
    private lateinit var layoutContainer: View
    private lateinit var builder: NotificationCompat.Builder
    private val permission = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        { isGranted: Boolean ->
            if (isGranted) {
                sendNofication()
            } else {
                Snackbar.make(
                    layoutContainer,
                    "ban chua cap quyen thong bao", Snackbar.LENGTH_INDEFINITE
                )
                    .show()
            }
        }
    )

    @SuppressLint("MissingPermission")
    private fun sendNofication() {
        with(NotificationManagerCompat.from(this)){
            notify(notificationId++,builder.build())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutContainer=findViewById(R.id.container_view)
        findViewById<Button>(R.id.btn_notification).setOnClickListener {
            createNotification()
            createNotificationChannel()
        }
    }

    fun createNotification() {
        val largeIcon= BitmapFactory.decodeResource(resources,R.drawable.ic_cat)
        val bigPicture=BitmapFactory.decodeResource(resources,R.drawable.cat1)
        val nullBitmap:Bitmap?=null
        var notificationContent = getString(R.string.notification_content) + " " +
                Utils.getDateTime()
        val intent= Intent(this,NotificationDetail::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(NotificationDetail.EXTRA_NOTIFICATION_CONTENT,notificationContent)
        }
        val pendingIntent= PendingIntent.getActivity(this,notificationId,intent,
            PendingIntent.FLAG_IMMUTABLE)
        builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setContentTitle(getString(R.string.conten_title))
            .setContentText(notificationContent)

            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(largeIcon)
            .setContentIntent(pendingIntent)
            .setStyle(MediaNotificationCompat.MediaStyle().setShowActionsInCompactView(1,2,3))
            .addAction(R.drawable.ic_skip_previous_24,"previous",null)
            .addAction(R.drawable.ic_pause_24,"pause",null)
            .addAction(R.drawable.ic_play_arrow_24,"play",null)
            .addAction(R.drawable.ic_skip_next_24,"next",null)
            .addAction(R.drawable.ic_repeat_24,"repeat",null)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)
        ){
            if (ActivityCompat.checkSelfPermission(baseContext,android.Manifest.permission.POST_NOTIFICATIONS)
            !=PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS))
                {
                    Snackbar.make(layoutContainer,"ban phai cung cap quyen truy cap",
                    Snackbar.LENGTH_INDEFINITE).show()
                }
                else{
                    permission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }

            }
            notify(notificationId++,builder.build())
        }


    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val nameChannel = getString(R.string.name_channel)
            val descriptionChannel = getString(R.string.description_channel)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, nameChannel, importance).apply {
                description = descriptionChannel
            }


            val notificationManager: NotificationManager = ContextCompat.getSystemService(
                baseContext, NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "CHANNEL_ID"
    }
}