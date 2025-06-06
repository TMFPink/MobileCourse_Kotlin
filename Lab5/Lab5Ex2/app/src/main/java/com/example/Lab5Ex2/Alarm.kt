package com.example.Lab5Ex2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val label: String,
    val isEnabled: Boolean = true,
    val repeatDays: String = "",
    val timeInMillis: Long = 0L
) {
    fun getTimeString(): String {
        return String.format("%02d:%02d", hour, minute)
    }

    fun getRepeatDaysString(): String {
        

        val days = repeatDays.split(",").map { it.toInt() }
        val dayNames = listOf("SD", "MD", "TD", "WD", "TsD", "FD", "StD")
        return days.map { dayNames[it] }.joinToString(", ")
    }
}