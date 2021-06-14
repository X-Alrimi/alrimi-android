package com.example.capstone2

import android.app.Application
import com.example.capstone2.core.SharedPreferenceHelper
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import timber.log.Timber

class BaseApplication: Application() {
    companion object{
        lateinit var prefs: SharedPreferenceHelper
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferenceHelper(applicationContext)

        val options : FirebaseOptions = FirebaseOptions.Builder()
            .setApplicationId("1:468838869553:android:7c42096014052221538310")
            .setProjectId("x-alrimi-508ee")
            .setApiKey("AIzaSyDNJRdKz_7w_W9FgYaKkV86cvZaRDZzvyE")
            .build()
        FirebaseApp.initializeApp(this, options, "com.example.capstone2")

        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}