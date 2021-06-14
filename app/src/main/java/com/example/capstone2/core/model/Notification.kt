package com.example.capstone2.core.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Notification(
        @SerializedName("createdAt") var createdAt: Date,
        @SerializedName("link") var link: String,
        @SerializedName("title") var title: String
)
