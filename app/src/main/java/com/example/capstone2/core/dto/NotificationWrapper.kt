package com.example.capstone2.core.dto

import com.example.capstone2.core.model.Notification

data class NotificationWrapper (
        var currentPage: Int,
        var news: ArrayList<Notification>,
        var totalPage: Int
        )