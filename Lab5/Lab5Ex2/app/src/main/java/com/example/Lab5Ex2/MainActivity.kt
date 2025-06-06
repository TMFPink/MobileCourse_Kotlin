package com.example.Lab5Ex2

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Lab5Ex2.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmScheduler: AlarmScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmScheduler = AlarmScheduler(this)

        binding.buttonSetAlarm.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            createAlarm(selectedHour, selectedMinute)
        }, hour, minute, true).show()
    }

    private fun createAlarm(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val alarm = Alarm(
            hour = hour,
            minute = minute,
            label = "Alarm",
            isEnabled = true,
            timeInMillis = calendar.timeInMillis
        )

        alarmScheduler.scheduleRepeatingAlarm(alarm)

        Toast.makeText(
            this,
            "Alarm set at ${alarm.getTimeString()}",
            Toast.LENGTH_SHORT
        ).show()
    }
}