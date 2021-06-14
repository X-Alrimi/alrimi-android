package com.example.capstone2.core.dto

import com.google.gson.annotations.SerializedName

data class GetKeywordResponse(
    @SerializedName("data") val keywords : ArrayList<String>
)
