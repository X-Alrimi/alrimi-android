package com.example.capstone2.feature.stock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone2.R
import com.example.capstone2.core.model.Stock
import com.example.capstone2.databinding.ActivityStockListBinding
import com.example.capstone2.feature.news.NewsListActivity

class StockListActivity: AppCompatActivity() {
    private lateinit var viewModel: StockListViewModel
    private lateinit var mBinding: ActivityStockListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        observeViewModel()
        initRecyclerView()
    }

    private fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_list)
        viewModel = ViewModelProvider(this).get(StockListViewModel::class.java)
        mBinding.vm = viewModel
    }

    private fun observeViewModel() {
        viewModel.onClickedStockCallback.observe(this, Observer {
            var intent = Intent(this, NewsListActivity::class.java)
            intent.putExtra("name", viewModel.curStock.name)
            startActivity(intent)
        })

        viewModel.onClickedBackCallback.observe(this, Observer {
            finish()
        })
    }

    private fun initRecyclerView() {
        var tmp : ArrayList<Stock> = ArrayList()
        tmp.add(Stock(1, "YG Entertainment"))
        tmp.add(Stock(2, "JYP Entertainment"))
        tmp.add(Stock(3,"FNC Entertainment"))
        tmp.add(Stock(4, "StarShip Entertainment"))
        tmp.add(Stock(5, "YG Entertainment"))
        tmp.add(Stock(6, "JYP Entertainment"))
        tmp.add(Stock(7, "FNC Entertainment"))
        tmp.add(Stock(8, "StarShip Entertainment"))
        tmp.add(Stock(9, "YG Entertainment"))
        tmp.add(Stock(10, "JYP Entertainment"))
        tmp.add(Stock(11, "FNC Entertainment"))
        tmp.add(Stock(12, "StarShip Entertainment"))
        val adapter = StockListRecyclerAdapter(tmp, viewModel)
        mBinding.rvStock.apply {
            this.adapter = adapter
        }
    }
}