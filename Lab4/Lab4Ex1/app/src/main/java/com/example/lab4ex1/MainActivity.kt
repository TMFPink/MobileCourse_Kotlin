package com.example.lab4ex1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4ex1.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isRunning = true
    private val handler = Handler(Looper.getMainLooper())
    private var seconds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start the timer and random updates automatically
        startTimer()
        startRandomUpdates()
    }

    private fun startTimer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isRunning) {
                    seconds++
                    val minutes = seconds / 60
                    val secs = seconds % 60
                    binding.timer.text = String.format("%02d:%02d", minutes, secs)
                    handler.postDelayed(this, 1000)
                }
            }
        }, 1000)
    }

    private fun startRandomUpdates() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isRunning) {
                    binding.heartRate.text = "${Random.nextInt(60, 120)}"
                    binding.caloriesBurned.text = "${Random.nextInt(50, 500)}"
                    binding.steps.text = "${Random.nextInt(1000, 10000)}"
                    handler.postDelayed(this, 2000)
                }
            }
        }, 2000)
    }
}
