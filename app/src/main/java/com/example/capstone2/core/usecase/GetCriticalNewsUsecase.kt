package com.example.capstone2.core.usecase

import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.NotificationResponse
import com.example.capstone2.core.service.NewsService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetCriticalNewsUsecase @Inject constructor(retrofit: Retrofit) {
    private val mNewsService = retrofit.mBuilder.create(NewsService::class.java)

    fun getCriticalNews(companyId: Long, page: Int): Observable<NotificationResponse> {
        return mNewsService.getCriticalNews(companyId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}