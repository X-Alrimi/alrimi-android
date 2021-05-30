package com.example.capstone2.feature.notification

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.capstone2.core.model.Notification
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotificationListViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    var stockName: MutableLiveData<String> = MutableLiveData()

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
}