package com.example.a24012011122_mad_pr4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("ALARM_MESSAGE") ?: "Alarm triggered!"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("ALARM_MESSAGE", message)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}