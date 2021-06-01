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

class NewsListActivity: AppCompatActivity() {
    private lateinit var viewModel: NewsListViewModel
    private lateinit var mBinding: ActivityNewsListBinding

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
            var intent = Intent(this, NewsWebViewActivity::class.java)
            intent.putExtra(Consts.NEWS_LINK, viewModel.curNews.link)
            startActivity(intent)
        })

        viewModel.onClickedNotificationListCallback.observe(this, Observer {
            var intent = Intent(this, NotificationListActivity::class.java)
            intent.putExtra(Consts.STOCK_NAME, viewModel.stockName.value)
            intent.putExtra(Consts.STOCK_ID, viewModel.stockId)
            startActivity(intent)
        })

        viewModel.onClickedGraphCallback.observe(this, Observer {
            var intent = Intent(this, StockGraphActivity::class.java)
            intent.putExtra(Consts.STOCK_GRAPH, Consts.getGraphLink(viewModel.stockId))
            startActivity(intent)
        })

        viewModel.newsList.observe(this, Observer {
            initRecyclerView()
        })
    }

    private fun initRecyclerView() {
        val adapter = NewsListRecyclerAdapter(viewModel.newsList.value!!, viewModel)
        mBinding.rvNews.apply {
            this.adapter = adapter
        }

        mBinding.rvNews.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (mBinding.rvNews.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = mBinding.rvNews.adapter?.itemCount
                if(lastVisibleItemPosition == itemTotalCount && viewModel.totalPage >= viewModel.currentPage) {
                    viewModel.getNews(viewModel.stockId, viewModel.currentPage)
                }
            }
        })
    }
}