package com.example.capstone2.feature.notification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import com.example.capstone2.databinding.ActivityNotificationListBinding
import com.example.capstone2.feature.news.NewsWebViewActivity

class NotificationListActivity: AppCompatActivity() {
    private lateinit var viewModel: NotificationListViewModel
    private lateinit var mBinding: ActivityNotificationListBinding

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
            var intent = Intent(this, NewsWebViewActivity::class.java)
            intent.putExtra("link", viewModel.curNotification.link)
            startActivity(intent)
        })

        viewModel.notificationList.observe(this, Observer {
            initRecyclerView()
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRecyclerView() {
        // Swipe Event
//        val swipeHelperCallback = SwipeHelperCallback().apply {
//            setClamp(dpToPx(applicationContext, 90f))
//        }
        val adapter = NotificationRecyclerAdapter(viewModel.notificationList.value!!, viewModel)

        // Swipe Event
//        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
//        itemTouchHelper.attachToRecyclerView(mBinding.rvNotification)
        mBinding.rvNotification.apply {
            this.adapter = adapter

            // Swipe Event
//            setOnTouchListener { _, _ ->
//                swipeHelperCallback.removePreviousClamp(this)
//                false
//            }
        }
    }
}