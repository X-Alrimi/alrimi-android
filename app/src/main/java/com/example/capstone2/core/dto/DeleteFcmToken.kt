package com.example.capstone2.core.dto

import com.google.gson.annotations.SerializedName

data class DeleteFcmToken(
        @SerializedName("token") val token: String
)
