package com.example.foregroundservicecrash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.browse.MediaBrowser
import android.os.Build
import android.os.Bundle
import android.service.media.MediaBrowserService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


private const val TAG = "MyForegroundService"
private const val REQUEST_FOREGROUND = "MyForegroundService.Foreground"
private const val REMOVE_FOREGROUND = "MyForegroundService.Remove.Foreground"

class MyForegroundService : MediaBrowserService() {

    companion object {
        fun requestForeground(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java).apply {
                action = REQUEST_FOREGROUND
            }
        }

        fun removeFromForeground(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java).apply {
                action = REMOVE_FOREGROUND
            }
        }
    }

    override fun onCreate() {
        Log.e(TAG, "Service onCreate -->")
        super.onCreate()
        Log.e(TAG, "Service onCreate <--")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.e(TAG, "onStartCommand --> action = $action")
        if (action == REQUEST_FOREGROUND) {
            Log.e(TAG, "onStartCommand requestForeground")
            requestForeground()
        } else if (action == REMOVE_FOREGROUND) {
            stopForeground(false)
            Log.e(TAG, "onStartCommand stopForeground <<--")
        }
        return START_STICKY
    }

    private fun requestForeground() {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, "demo_channel")
            .setContentTitle("MyForegroundService")
            .build()
        startForeground(123, notification)
        Log.e(TAG, "requestForeground complete <<--")
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return null
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowser.MediaItem>>
    ) {

    }

    override fun stopService(name: Intent?): Boolean {
        Log.e(TAG, "stopService -->>")
        return super.stopService(name).also {
            Log.e(TAG, "stopService <<--")
        }
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy -->")
        super.onDestroy()
        Log.e(TAG, "onDestroy <<--")
    }

    private fun createNotificationChannel() {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(
                NotificationChannel(
                    "demo_channel",
                    "Demo Channel",
                    NotificationManager.IMPORTANCE_NONE
                )
            )
    }
}