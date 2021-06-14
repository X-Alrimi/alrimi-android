package com.example.capstone2.feature.main

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.DeleteFcmToken
import com.example.capstone2.core.dto.PostFcmToken
import com.example.capstone2.core.usecase.DeleteFcmTokenUsecase
import com.example.capstone2.core.usecase.RegisterFcmTokenUsecase
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application){
    private val registerFcmTokenUsecase = RegisterFcmTokenUsecase(Retrofit)
    private val deleteFcmTokenUsecase = DeleteFcmTokenUsecase(Retrofit)

    val onClickedStartButton: SingleLiveEvent<Void> = SingleLiveEvent()

    var token = "";

    fun goToStockList(view: View) {
        onClickedStartButton.call()
    }

    @SuppressLint("CheckResult")
    fun registerFcmToken(token: PostFcmToken) {
        registerFcmTokenUsecase.registerFcmToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("token registered")
                }, { e ->
                    Timber.e("error message : ${e.localizedMessage}")
                })
    }

    @SuppressLint("CheckResult")
    fun deleteFcmToken(token: DeleteFcmToken) {
        deleteFcmTokenUsecase.deleteFcmToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("token deleted")
                }, { e ->
                    Timber.e("error message : ${e.localizedMessage}")
                })
    }
}