package com.example.capstone2.feature.news

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.capstone2.core.model.News
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsListViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    var stockName: MutableLiveData<String> = MutableLiveData()

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
}