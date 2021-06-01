package com.example.capstone2.feature.notification

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.model.Notification
import com.example.capstone2.core.usecase.GetCriticalNewsUsecase
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotificationListViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    private val getCriticalNewsUsecase = GetCriticalNewsUsecase(Retrofit)

    var stockName: SingleLiveEvent<String> = SingleLiveEvent()
    var stockId: Long = -1
    var notificationList: SingleLiveEvent<ArrayList<Notification>> = SingleLiveEvent()
    var currentPage: Int = 1
    var totalPage: Int = 0
    init {
        notificationList.value = ArrayList()
    }

    val onClickedBackCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val onClickedNotificationCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    lateinit var curNotification: Notification

    fun setCreatedAt(notification: Notification): String {
        val cal = Calendar.getInstance()
        cal.time = notification.createdAt
        val df: DateFormat = SimpleDateFormat("YYYY/MM/dd HH:mm")

        return "${df.format(cal.time)}"
    }

    fun onClickedNotification(notification: Notification) {
        curNotification = notification
        onClickedNotificationCallback.call()
    }

    fun onClickedBack(view: View) {
        onClickedBackCallback.call()
    }

    @SuppressLint("CheckResult")
    fun getCriticalNews(companyId: Long, page: Int) {
        getCriticalNewsUsecase.getCriticalNews(companyId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Timber.d("get critical news")
                    if(notificationList.value!!.isEmpty()) notificationList.value = response.data.news
                    else notificationList.value!!.addAll(response.data.news)
                    totalPage = response.data.totalPage
                    currentPage++
                }, { e ->
                    Timber.e("error message : ${e.localizedMessage}")
                })
    }
}