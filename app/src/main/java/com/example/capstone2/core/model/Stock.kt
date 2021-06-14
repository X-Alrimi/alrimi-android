package com.example.capstone2.core.model

import com.google.gson.annotations.SerializedName

data class Stock (
        @SerializedName("id") var id: Long,
        @SerializedName("name") var name: String
        )