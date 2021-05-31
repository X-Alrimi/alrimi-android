package com.example.capstone2.core.dto

import com.example.capstone2.core.model.News

data class NewsWrapper(
        var currentPage: Int,
        var news: ArrayList<News>,
        var totalPage: Int
)
