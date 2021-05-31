package com.example.capstone2.feature.stock

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.model.Stock
import com.example.capstone2.core.usecase.GetStockListUsecase
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class StockListViewModel @Inject constructor(application: Application) : AndroidViewModel(application){
    private val getStockListUsecase = GetStockListUsecase(Retrofit)

    val onClickedStockCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val onClickedBackCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    lateinit var curStock: Stock
    var stockList: SingleLiveEvent<ArrayList<Stock>> = SingleLiveEvent()
    init {
        stockList.value = ArrayList()
    }

    fun onClickedStock(stock: Stock) {
        curStock = stock
        onClickedStockCallback.call()
    }

    fun onClickedBack(view: View) {
        onClickedBackCallback.call()
    }

    @SuppressLint("CheckResult")
    fun getStockList() {
        getStockListUsecase.getStockList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    stockList.value = response.data
                    Timber.d("stockList : ${stockList.value}")
                }, { e ->
                    Timber.e("error message : ${e.localizedMessage}")
                })
    }
}