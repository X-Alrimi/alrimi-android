package com.example.capstone2.core.dto

import com.example.capstone2.core.model.Stock
import com.fasterxml.jackson.annotation.JsonProperty

data class StockListResponse (
    @JsonProperty("") var stockList: ArrayList<Stock>
        )