package com.example.capstone2.feature.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import com.example.capstone2.core.model.News
import com.example.capstone2.databinding.ActivityNewsListBinding
import com.example.capstone2.feature.notification.NotificationListActivity
import com.example.capstone2.feature.stock.StockGraphActivity
import java.sql.Date

class NewsListActivity: AppCompatActivity() {
    private lateinit var viewModel: NewsListViewModel
    private lateinit var mBinding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        observeViewModel()
        initRecyclerView()
    }

    private fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        mBinding.vm = viewModel

        // 종목 이름 넘겨받기
        val name = intent.getStringExtra(Consts.EXTRA_NAME)
        viewModel.stockName.value = name
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
            intent.putExtra(Consts.EXTRA_LINK, viewModel.curNews.link)
            intent.putExtra(Consts.EXTRA_NAME, viewModel.stockName.value)
            startActivity(intent)
        })

        viewModel.onClickedNotificationListCallback.observe(this, Observer {
            var intent = Intent(this, NotificationListActivity::class.java)
            intent.putExtra(Consts.EXTRA_NAME, viewModel.stockName.value)
            startActivity(intent)
        })

        viewModel.onClickedGraphCallback.observe(this, Observer {
            var intent = Intent(this, StockGraphActivity::class.java)
            intent.putExtra(Consts.EXTRA_GRAPH, "https://finance.naver.com/item/main.nhn?code=122870")
            startActivity(intent)
        })
    }

    private fun initRecyclerView() {
        var tmp = ArrayList<News>()
        tmp.add(News("한류스타 김태준 여자친구 생겨... \"충격\"ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ", "https://www.naver.com", Date(System.currentTimeMillis())))
        tmp.add(News("한류스타 김태준", "https://entertain.naver.com/read?oid=005&aid=0001177800", Date(System.currentTimeMillis())))
        tmp.add(News("한류스타 김태준 여자친구 생겨... \"충격\"", "https://entertain.naver.com/read?oid=112&aid=0003344355", Date(System.currentTimeMillis())))
        tmp.add(News("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(News("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(News("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(News("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(News("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        val adapter = NewsListRecyclerAdapter(tmp, viewModel)
        mBinding.rvNews.apply {
            this.adapter = adapter
        }

    }
}