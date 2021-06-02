package com.example.capstone2.core.service

import com.example.capstone2.core.dto.NewsResponse
import com.example.capstone2.core.dto.NotificationResponse
import io.reactivex.Observable
import retrofit2.http.*

interface NewsService {
    @GET("news")
    fun getNews(@Query("companyId") companyId: Long, @Query("page") page: Int): Observable<NewsResponse>

    @GET("news/critical")
    fun getCriticalNews(@Query("companyId") companyId: Long, @Query("page") page: Int): Observable<NotificationResponse>
}