package com.example.capstone2.core.dto

import com.example.capstone2.core.model.Stock
import com.google.gson.annotations.SerializedName

data class StockResponse(
    @SerializedName("company") val stock: Stock
)
