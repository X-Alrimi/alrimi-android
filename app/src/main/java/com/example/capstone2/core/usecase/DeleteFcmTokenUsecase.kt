package com.example.capstone2.core.usecase

import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.DefaultResponse
import com.example.capstone2.core.dto.DeleteFcmToken
import com.example.capstone2.core.service.DeviceService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeleteFcmTokenUsecase @Inject constructor(retrofit: Retrofit) {
    private val mDeviceService = retrofit.mBuilder.create(DeviceService::class.java)

    fun deleteFcmToken(token: DeleteFcmToken): Single<DefaultResponse> {
        return mDeviceService.deleteFcmToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}