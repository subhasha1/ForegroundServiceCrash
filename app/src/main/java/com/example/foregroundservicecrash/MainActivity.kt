package com.example.foregroundservicecrash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start).setOnClickListener {
            startForegroundService(MyForegroundService.requestForeground(this))
        }

        findViewById<Button>(R.id.removeFromForeground).setOnClickListener {
            startService(MyForegroundService.removeFromForeground(this))
        }

        findViewById<Button>(R.id.startForegroundAndStopService).setOnClickListener {
            val serviceIntent = MyForegroundService.requestForeground(this)
            startForegroundService(serviceIntent)
            Handler().post { stopService(serviceIntent) }
        }

        findViewById<Button>(R.id.startBackgroundAndStopService).setOnClickListener {
            val serviceIntent = MyForegroundService.requestForeground(this)
            startService(serviceIntent)
            Handler().post { stopService(serviceIntent) }
        }
    }
}
