package com.example.capstone2.feature.notification

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.capstone2.R
import com.example.capstone2.core.model.Notification
import com.example.capstone2.databinding.ActivityNotificationListBinding
import com.example.capstone2.feature.news.NewsWebViewActivity
import java.sql.Date

class NotificationListActivity: AppCompatActivity() {
    private lateinit var viewModel: NotificationListViewModel
    private lateinit var mBinding: ActivityNotificationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        observeViewModel()
        initRecyclerView()

        // 종목 이름 받기
        val name = intent.getStringExtra("name")
        viewModel.stockName.value = name
    }

    private fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification_list)
        viewModel = ViewModelProvider(this).get(NotificationListViewModel::class.java)
        mBinding.vm = viewModel
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
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRecyclerView() {
        var tmp = ArrayList<Notification>()
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ", "https://www.naver.com", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준", "https://entertain.naver.com/read?oid=005&aid=0001177800", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "https://entertain.naver.com/read?oid=112&aid=0003344355", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))
        tmp.add(Notification("한류스타 김태준 여자친구 생겨... \"충격\"", "www.love.com", Date(System.currentTimeMillis())))

        val swipeHelperCallback = SwipeHelperCallback().apply {
            setClamp(dpToPx(applicationContext, 90f))
        }
        val adapter = NotificationRecyclerAdapter(tmp, viewModel)

        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(mBinding.rvNotification)
        mBinding.rvNotification.apply {
            this.adapter = adapter

            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
        }
    }

    // dp 수치를 px 수치로 변환해주는 함수
    fun dpToPx(context : Context, dp : Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}