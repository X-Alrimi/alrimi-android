package com.example.capstone2.core.dto

import com.google.gson.annotations.SerializedName

data class StockListResponse (
        @SerializedName("data") var data: ArrayList<StockWrapper>
        )