package eu.tutorials.a7_minutesworkoutapp

import android.app.Application

class WorkoutApp:Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}