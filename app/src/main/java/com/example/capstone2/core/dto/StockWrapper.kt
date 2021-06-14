package com.example.capstone2.core.dto

import com.example.capstone2.core.model.News
import com.example.capstone2.core.model.Stock
import com.google.gson.annotations.SerializedName

data class StockWrapper(
        @SerializedName("company") val company: Stock,
        @SerializedName("news") val news: ArrayList<News>
)
