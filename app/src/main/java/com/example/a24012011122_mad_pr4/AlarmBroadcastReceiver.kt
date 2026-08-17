package com.example.a24012011122_mad_pr4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val SERVICE_KEY = "service"
        const val START_VAL = "start"
        const val STOP_VAL = "stop"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val command = intent.getStringExtra(SERVICE_KEY)
        val serviceIntent = Intent(context, AlarmService::class.java)

        if (command == STOP_VAL) {
            context.stopService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}