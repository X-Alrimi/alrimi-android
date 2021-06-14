package com.example.capstone2.core.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class News (
        @SerializedName("title") var title: String,
        @SerializedName("link") var link: String,
        @SerializedName("createdAt") var createdAt: Date
        )