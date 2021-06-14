package com.example.capstone2.feature.stock

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.capstone2.core.Retrofit
import com.example.capstone2.core.dto.KeywordDto
import com.example.capstone2.core.dto.StockWrapper
import com.example.capstone2.core.model.Stock
import com.example.capstone2.core.usecase.AddKeywordUsecase
import com.example.capstone2.core.usecase.DeleteKeywordUsecase
import com.example.capstone2.core.usecase.GetKeywordUsecase
import com.example.capstone2.core.usecase.GetStockListUsecase
import com.example.capstone2.feature.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class StockListViewModel @Inject constructor(application: Application) : AndroidViewModel(application){
    private val getStockListUsecase = GetStockListUsecase(Retrofit)
    private val addKeywordUsecase = AddKeywordUsecase(Retrofit)
    private val deleteKeywordUsecase = DeleteKeywordUsecase(Retrofit)
    private val getKeywordUsecase = GetKeywordUsecase(Retrofit)

    val onClickedStockCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val onClickedBackCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    var token = ""
    var keywords = ArrayList<String>()
    var isYgNotificationOn = false

    lateinit var curStock: Stock
    var stockList: SingleLiveEvent<ArrayList<StockWrapper>> = SingleLiveEvent()
    init {
        stockList.value = ArrayList()
    }

    fun onClickedStock(stock: StockWrapper) {
        curStock = stock.company
        onClickedStockCallback.call()
    }

    fun onClickedBack(view: View) {
        onClickedBackCallback.call()
    }

    fun notificationSet() {
        for(i in 0 until keywords.size) {
            if(keywords[i].equals("YG")) isYgNotificationOn = true
        }
    }

    @SuppressLint("CheckResult")
    fun getStockList() {
        getStockListUsecase.getStockList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    stockList.value = response.data
                    Timber.d("stockList : ${stockList.value}")

                    // 회사 리스트 받은 후 내가 구독한 키워드 받기
                    getKeyword(token)
                }, { e ->
                    Timber.e("error message : ${e.localizedMessage}")
                })
    }

    @SuppressLint("CheckResult")
    fun addKeyword(keywordDto: KeywordDto) {
        addKeywordUsecase.addKeyword(keywordDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                // nothing
                Timber.d("add keyword : $response")
            }, { e ->
                Timber.e("error message : ${e.localizedMessage}")
        })
    }

    @SuppressLint("CheckResult")
    fun deleteKeyword(keywordDto: KeywordDto) {
        deleteKeywordUsecase.deleteKeyword(keywordDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                // nothing
                Timber.d("delete keyword : $response")
            }, { e ->
                Timber.e("error message : ${e.localizedMessage}")
            })
    }

    @SuppressLint("CheckResult")
    fun getKeyword(token: String) {
        getKeywordUsecase.getKeyword(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Timber.d("get keyword : $response")
                keywords = response.keywords
                notificationSet()
            }, { e ->
                Timber.e("error message : ${e.localizedMessage}")
            })
    }
}