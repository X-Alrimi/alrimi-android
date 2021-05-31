package com.example.capstone2.feature.stock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import com.example.capstone2.databinding.ActivityStockListBinding
import com.example.capstone2.feature.news.NewsListActivity
import timber.log.Timber

class StockListActivity: AppCompatActivity() {
    private lateinit var viewModel: StockListViewModel
    private lateinit var mBinding: ActivityStockListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        observeViewModel()
        viewModel.getStockList()
        //initRecyclerView()
    }

    private fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_list)
        viewModel = ViewModelProvider(this).get(StockListViewModel::class.java)
        mBinding.vm = viewModel
    }

    private fun observeViewModel() {
        viewModel.onClickedStockCallback.observe(this, Observer {
            var intent = Intent(this, NewsListActivity::class.java)
            intent.putExtra(Consts.STOCK_NAME, viewModel.curStock.name)
            intent.putExtra(Consts.STOCK_ID, viewModel.curStock.id)
            startActivity(intent)
        })

        viewModel.onClickedBackCallback.observe(this, Observer {
            finish()
        })

        viewModel.stockList.observe(this, Observer {
            initRecyclerView()
        })
    }

    private fun initRecyclerView() {
        // 더미 데이터
//        var tmp : ArrayList<Stock> = ArrayList()
//        tmp.add(Stock(1, "YG Entertainment"))
//        tmp.add(Stock(2, "JYP Entertainment"))
//        tmp.add(Stock(3,"FNC Entertainment"))
//        tmp.add(Stock(4, "StarShip Entertainment"))
//        tmp.add(Stock(5, "YG Entertainment"))
//        tmp.add(Stock(6, "JYP Entertainment"))
//        tmp.add(Stock(7, "FNC Entertainment"))
//        tmp.add(Stock(8, "StarShip Entertainment"))
//        tmp.add(Stock(9, "YG Entertainment"))
//        tmp.add(Stock(10, "JYP Entertainment"))
//        tmp.add(Stock(11, "FNC Entertainment"))
//        tmp.add(Stock(12, "StarShip Entertainment"))

        Timber.d("stock: ${viewModel.stockList.value}")
        val adapter = StockListRecyclerAdapter(viewModel.stockList.value!!, viewModel)
        mBinding.rvStock.apply {
            this.adapter = adapter
            Timber.d("stock item count : ${adapter.itemCount}")
        }
    }
}