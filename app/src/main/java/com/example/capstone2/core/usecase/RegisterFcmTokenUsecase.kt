package com.example.capstone2.core.usecase

import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.DefaultResponse
import com.example.capstone2.core.dto.PostFcmToken
import com.example.capstone2.core.service.DeviceService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterFcmTokenUsecase @Inject constructor(retrofit: Retrofit){
    private val mDeviceService = retrofit.mBuilder.create(DeviceService::class.java)

    fun registerFcmToken(token: PostFcmToken) : Single<DefaultResponse> {
        return mDeviceService.registerFcmToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}