package com.example.capstone2.feature.main

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application){
    val onClickedStartButton: SingleLiveEvent<Void> = SingleLiveEvent()

    fun goToStockList(view: View) {
        onClickedStartButton.call()
    }
}