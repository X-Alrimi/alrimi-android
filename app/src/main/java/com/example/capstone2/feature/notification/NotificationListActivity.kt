package com.example.capstone2.feature.notification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import com.example.capstone2.databinding.ActivityNotificationListBinding
import com.example.capstone2.feature.news.NewsWebViewActivity

class NotificationListActivity: AppCompatActivity() {
    private lateinit var viewModel: NotificationListViewModel
    private lateinit var mBinding: ActivityNotificationListBinding
    private lateinit var mAdapter: NotificationRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        observeViewModel()
        viewModel.getCriticalNews(viewModel.stockId, viewModel.currentPage)
    }

    private fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification_list)
        viewModel = ViewModelProvider(this).get(NotificationListViewModel::class.java)
        mBinding.vm = viewModel

        // 종목 이름 받기
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

        viewModel.onClickedNotificationCallback.observe(this, Observer {
            val intent = Intent(this, NewsWebViewActivity::class.java)
            intent.putExtra(Consts.NEWS_LINK, viewModel.curNotification.link)
            startActivity(intent)
        })

        viewModel.notificationList.observe(this, Observer {
            if(viewModel.notificationList.value!!.isNotEmpty()) initRecyclerView()
        })

        viewModel.moreNotificationCallback.observe(this, Observer {
            mAdapter.addNotification()
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRecyclerView() {
        // Swipe Event
//        val swipeHelperCallback = SwipeHelperCallback().apply {
//            setClamp(dpToPx(applicationContext, 110f))
//        }
        mAdapter = NotificationRecyclerAdapter(viewModel.notificationList.value!!, viewModel)

        // Swipe Event
//        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
//        itemTouchHelper.attachToRecyclerView(mBinding.rvNotification)
        mBinding.rvNotification.apply {
            this.adapter = mAdapter

            // Swipe Event
//            setOnTouchListener { _, _ ->
//                swipeHelperCallback.removePreviousClamp(this)
//                false
//            }
        }

        // 페이징 처리
        mBinding.rvNotification.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (mBinding.rvNotification.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = mBinding.rvNotification.adapter?.itemCount
                if(lastVisibleItemPosition == itemTotalCount!!-1 && viewModel.totalPage >= viewModel.currentPage) {
                    viewModel.getCriticalNews(viewModel.stockId, viewModel.currentPage)
                }
            }
        })
    }
}