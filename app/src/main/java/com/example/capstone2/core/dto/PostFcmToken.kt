package com.example.capstone2.core.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class PostFcmToken(
        @SerializedName("token") val token: String
)
