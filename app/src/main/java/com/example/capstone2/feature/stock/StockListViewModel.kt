package com.example.capstone2.feature.stock

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.capstone2.core.model.Stock
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class StockListViewModel @Inject constructor(application: Application) : AndroidViewModel(application){
    val onClickedStockCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val onClickedBackCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    lateinit var curStock: Stock

    fun onClickedStock(stock: Stock) {
        Timber.e("stock clicked")
        curStock = stock
        onClickedStockCallback.call()
    }

    fun onClickedBack(view: View) {
        Timber.e("back clicked")
        onClickedBackCallback.call()
    }
}