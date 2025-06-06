package com.example.Lab5Ex2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Lab5Ex2.databinding.ActivityAlarmRingBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AlarmRingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmRingBinding
    private lateinit var alarmScheduler: AlarmScheduler



override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAlarmRingBinding.inflate(layoutInflater)
    setContentView(binding.root)

    alarmScheduler = AlarmScheduler(this)

    val alarmId = intent.getIntExtra("alarm_id", -1)

    
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(System.currentTimeMillis())
    binding.textViewAlarmTime.text = currentTime 

    binding.buttonSnooze.setOnClickListener {
        snoozeAlarm(alarmId)
        stopService(Intent(this, AlarmService::class.java))
        finish()
    }

    binding.buttonDismiss.setOnClickListener {
        dismissAlarm(alarmId)
        stopService(Intent(this, AlarmService::class.java))
        finish()
    }

        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    private fun getAlarmLabel(alarmId: Int): String? {
        return intent.getStringExtra("alarm_label") ?: "Báo thức"
    }

    private fun snoozeAlarm(alarmId: Int) {
        if (alarmId != -1) {
            alarmScheduler.scheduleSnoozeAlarm(alarmId)
        } else return
    }

    private fun dismissAlarm(alarmId: Int) {
        if (alarmId != -1) {
            alarmScheduler.cancelAlarm(alarmId)
            Toast.makeText(this, "Alarm off", Toast.LENGTH_SHORT).show()
        } else return
    }

    override fun onDestroy() {
        super.onDestroy()
        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}