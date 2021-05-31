package com.example.capstone2

import android.app.Application
import com.example.capstone2.core.SharedPreferenceHelper
import timber.log.Timber

class BaseApplication: Application() {
    companion object{
        lateinit var prefs: SharedPreferenceHelper
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferenceHelper(applicationContext)

        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}