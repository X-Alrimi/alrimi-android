package com.example.capstone2.core.usecase

import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.NewsResponse
import com.example.capstone2.core.service.NewsService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetNewsUsecase @Inject constructor(retrofit: Retrofit){
    private val mNewsService = retrofit.mBuilder.create(NewsService::class.java)

    fun getNews(companyId: Long, page: Int): Observable<NewsResponse> {
        return mNewsService.getNews(companyId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}