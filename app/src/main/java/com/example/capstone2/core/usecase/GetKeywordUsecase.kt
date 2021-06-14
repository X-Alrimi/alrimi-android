package com.example.capstone2.core.usecase

import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.GetKeywordResponse
import com.example.capstone2.core.service.DeviceService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetKeywordUsecase @Inject constructor(retrofit: Retrofit){
    private val mDeviceService = retrofit.mBuilder.create(DeviceService::class.java)

    fun getKeyword(token: String) : Single<GetKeywordResponse> {
        return mDeviceService.getKeyword(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}