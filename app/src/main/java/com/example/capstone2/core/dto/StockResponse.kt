package com.example.capstone2.core.dto

import com.example.capstone2.core.model.Stock
import com.fasterxml.jackson.annotation.JsonProperty

data class StockResponse(
    @JsonProperty("company") val stock: Stock
)
