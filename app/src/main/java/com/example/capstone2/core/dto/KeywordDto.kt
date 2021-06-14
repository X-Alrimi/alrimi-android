package com.example.capstone2.core.dto

import com.google.gson.annotations.SerializedName

data class KeywordDto(
    @SerializedName("token") val token : String,
    @SerializedName("keyword") val stockName : String
)
