package com.example.capstone2.feature.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import com.example.capstone2.databinding.ActivityNewsListBinding
import com.example.capstone2.feature.notification.NotificationListActivity
import com.example.capstone2.feature.stock.StockGraphActivity
import timber.log.Timber

class NewsListActivity: AppCompatActivity() {
    private lateinit var viewModel: NewsListViewModel
    private lateinit var mBinding: ActivityNewsListBinding
    private lateinit var mAdapter: NewsListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        observeViewModel()
        viewModel.getNews(viewModel.stockId, viewModel.currentPage)
    }

    private fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        mBinding.vm = viewModel

        // 종목 이름 넘겨받기
        val name = intent.getStringExtra(Consts.STOCK_NAME)
        val id = intent.getLongExtra(Consts.STOCK_ID, -1)
        viewModel.stockName.value = name
        viewModel.stockId = id
    }

    private fun observeViewModel() {
        viewModel.stockName.observe(this, Observer {
            mBinding.tvStockName.text = it
        })

        viewModel.onClickedBackCallback.observe(this, Observer {
            finish()
        })

        viewModel.onClickedLinkCallback.observe(this, Observer {
            val intent = Intent(this, NewsWebViewActivity::class.java)
            intent.putExtra(Consts.NEWS_LINK, viewModel.curNews.link)
            startActivity(intent)
        })

        viewModel.onClickedNotificationListCallback.observe(this, Observer {
            val intent = Intent(this, NotificationListActivity::class.java)
            intent.putExtra(Consts.STOCK_NAME, viewModel.stockName.value)
            intent.putExtra(Consts.STOCK_ID, viewModel.stockId)
            startActivity(intent)
        })

        viewModel.onClickedGraphCallback.observe(this, Observer {
            val intent = Intent(this, StockGraphActivity::class.java)
            intent.putExtra(Consts.STOCK_GRAPH, Consts.getGraphLink(viewModel.stockId))
            startActivity(intent)
        })

        viewModel.newsList.observe(this, Observer {
            Timber.d("list count : ${viewModel.newsList.value!!.size}\nnews list : ${viewModel.newsList.value}")
            if(viewModel.newsList.value!!.isNotEmpty()) initRecyclerView()
        })

        viewModel.moreNewsCallback.observe(this, Observer {
            mAdapter.addNews()
        })
    }

    private fun initRecyclerView() {
        Timber.d("init recycler")
        mAdapter = NewsListRecyclerAdapter(viewModel.newsList.value!!, viewModel)
        mBinding.rvNews.apply {
            this.adapter = mAdapter
        }

        // 페이징 처리
        mBinding.rvNews.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (mBinding.rvNews.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = mBinding.rvNews.adapter?.itemCount
                if(lastVisibleItemPosition == itemTotalCount!!-1 && viewModel.totalPage >= viewModel.currentPage) {
                    viewModel.getNews(viewModel.stockId, viewModel.currentPage)
                }
            }
        })
    }
}