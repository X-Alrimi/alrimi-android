package com.example.capstone2.core.dto

import com.example.capstone2.core.model.News
import com.fasterxml.jackson.annotation.JsonProperty

data class NewsResponse(
    @JsonProperty("currentPage") var currentPage: Int,
    @JsonProperty("news") var newsList: ArrayList<News>,
    @JsonProperty("totalPage") var totalPage: Int
)
