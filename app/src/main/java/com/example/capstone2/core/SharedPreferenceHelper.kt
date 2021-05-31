package com.example.capstone2.core

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Consts.SF_FILE_NAME, 0)

    var token: String?
        get() = sharedPreferences.getString(Consts.SF_TOKEN, "no token")
        set(newToken) = sharedPreferences.edit().putString(Consts.SF_TOKEN, newToken).apply()
}