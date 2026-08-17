package com.example.a24012011122_mad_pr4

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateAlarm: Button
    private lateinit var btnCancelAlarm: Button
    private lateinit var cardBottom: CardView
    private lateinit var tvSetAlarmValue: TextView

    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        btnCreateAlarm = findViewById(R.id.btnCreateAlarm)
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm)
        cardBottom = findViewById(R.id.cardBottom)
        tvSetAlarmValue = findViewById(R.id.tvSetAlarmValue)

        btnCreateAlarm.setOnClickListener {
            showTimePickerDialog()
        }

        btnCancelAlarm.setOnClickListener {
            cancelAlarm()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                setAlarm(selectedHour, selectedMinute)
            },
            hour,
            minute,
            false
        )
        timePickerDialog.show()
    }

    private fun setAlarm(hour: Int, minute: Int) {
        val alarmCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (alarmCalendar.before(Calendar.getInstance())) {
            alarmCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val pendingIntent = getAlarmPendingIntent(AlarmBroadcastReceiver.START_VAL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmCalendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmCalendar.timeInMillis,
                pendingIntent
            )
        }

        val sdf = SimpleDateFormat("hh:mm 00 a", Locale.getDefault())
        val formattedTime = sdf.format(alarmCalendar.time)

        tvSetAlarmValue.text = formattedTime
        cardBottom.visibility = View.VISIBLE

        Toast.makeText(this, "Alarm set for $formattedTime", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        val pendingIntent = getAlarmPendingIntent(AlarmBroadcastReceiver.START_VAL)
        alarmManager.cancel(pendingIntent)

        val stopIntent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            putExtra(AlarmBroadcastReceiver.SERVICE_KEY, AlarmBroadcastReceiver.STOP_VAL)
        }
        sendBroadcast(stopIntent)
        stopService(Intent(this, AlarmService::class.java))

        cardBottom.visibility = View.GONE

        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun getAlarmPendingIntent(command: String): PendingIntent {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            putExtra(AlarmBroadcastReceiver.SERVICE_KEY, command)
        }
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        return PendingIntent.getBroadcast(this, 0, intent, flags)
    }
}