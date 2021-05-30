package com.example.capstone2.core.service

import com.example.capstone2.core.dto.DefaultResponse
import io.reactivex.Single
import retrofit2.http.*

interface DeviceService {
    @POST("device/token")
    fun registerFcmToken(@Body token: String): Single<DefaultResponse>

    @DELETE("device/token")
    fun deleteFcmToken(@Body token: String): Single<DefaultResponse>
}