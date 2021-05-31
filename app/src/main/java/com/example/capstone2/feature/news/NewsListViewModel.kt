package com.example.capstone2.feature.news

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.model.News
import com.example.capstone2.core.usecase.GetNewsUsecase
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NewsListViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    private val getNewsUsecase = GetNewsUsecase(Retrofit)

    var stockName: SingleLiveEvent<String> = SingleLiveEvent()
    var stockId: Long = -1
    var newsList: SingleLiveEvent<ArrayList<News>> = SingleLiveEvent()
    var currentPage: Int = 1
    var totalPage: Int = 0
    init {
        newsList.value = ArrayList()
    }

    val onClickedBackCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val onClickedLinkCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val onClickedNotificationListCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val onClickedGraphCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    lateinit var curNews: News

    fun setCreatedAt(news: News): String {
        val cal = Calendar.getInstance()
        cal.time = news.createdAt
        val df: DateFormat = SimpleDateFormat("YYYY/MM/dd HH:mm")

        return "${df.format(cal.time)}"
    }

    fun onClickedBack(view: View) {
        onClickedBackCallback.call()
    }

    fun onClickedLink(news: News) {
        curNews = news
        onClickedLinkCallback.call()
    }

    fun onClickedNotificationList(view: View) {
        onClickedNotificationListCallback.call()
    }

    fun onClickedGraph(view: View) {
        onClickedGraphCallback.call()
    }

    @SuppressLint("CheckResult")
    fun getNews(companyId: Long, page: Int) {
        getNewsUsecase.getNews(companyId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Timber.d("get news")

                    if(newsList.value!!.isEmpty()) newsList.value = response.data.news
                    else newsList.value!!.addAll(response.data.news)
                    totalPage = response.data.totalPage
                    currentPage++
                }, { e ->
                    Timber.e("error message : ${e.localizedMessage}")
                })
    }
}