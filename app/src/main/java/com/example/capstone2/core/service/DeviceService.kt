package com.example.capstone2.core.service

import com.example.capstone2.core.dto.DefaultResponse
import com.example.capstone2.core.dto.DeleteFcmToken
import com.example.capstone2.core.dto.PostFcmToken
import io.reactivex.Single
import retrofit2.http.*

interface DeviceService {
    @POST("device/token")
    fun registerFcmToken(@Body token: PostFcmToken): Single<DefaultResponse>

    @DELETE("device/token")
    fun deleteFcmToken(@Body token: DeleteFcmToken): Single<DefaultResponse>
}