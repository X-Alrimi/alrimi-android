package com.example.capstone2.core.service

import com.example.capstone2.core.dto.*
import io.reactivex.Single
import retrofit2.http.*

interface DeviceService {
    @POST("device/token")
    fun registerFcmToken(@Body token: PostFcmToken): Single<DefaultResponse>

    @DELETE("device/token")
    fun deleteFcmToken(@Body token: DeleteFcmToken): Single<Boolean>

    @POST("device/keyword")
    fun addKeyword(@Body keywordDto: KeywordDto) : Single<DefaultResponse>

    @DELETE("device/keyword")
    fun deleteKeyword(@Body keywordDto: KeywordDto) : Single<Boolean>

    @POST("device/mykeyword")
    fun getKeyword(@Query("dto") token : String) : Single<GetKeywordResponse>
}