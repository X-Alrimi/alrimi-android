package com.example.capstone2.core.service

import com.example.capstone2.core.dto.StockListResponse
import com.example.capstone2.core.dto.StockResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface StockService {
    @GET("companies")
    fun getStockList(): Observable<StockListResponse>
}