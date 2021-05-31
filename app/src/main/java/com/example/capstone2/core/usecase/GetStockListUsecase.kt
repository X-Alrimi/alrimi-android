package com.example.capstone2.core.usecase

import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.StockListResponse
import com.example.capstone2.core.service.StockService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetStockListUsecase @Inject constructor(retrofit: Retrofit) {
    private val mStockService = retrofit.mBuilder.create(StockService::class.java)

    fun getStockList(): Observable<StockListResponse>{
        return mStockService.getStockList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}