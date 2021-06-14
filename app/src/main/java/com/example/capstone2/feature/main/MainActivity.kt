package com.example.capstone2.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone2.BaseApplication
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import com.example.capstone2.core.dto.DeleteFcmToken
import com.example.capstone2.core.dto.PostFcmToken
import com.example.capstone2.databinding.ActivityMainBinding
import com.example.capstone2.feature.stock.StockListActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        observeViewModel()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.w(task.exception, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }

            // 현재 토큰 받아오고, 로컬 DB에 저장된 이전 토큰과 비교
            val token = task.result!!
            val beforeToken = BaseApplication.prefs.token!!

            // 이전에 로컬 DB에 저장된 토큰이 없으면 저장하고 서버에 등록
            if(beforeToken == "no token") {
                BaseApplication.prefs.token = token
                viewModel.registerFcmToken(PostFcmToken(token))
                Timber.d("no token area")
            }
            // 현재 토큰과 이전 토큰이 다르다 == 새로운 토큰이 발급됐다 -> 로컬 DB에 새 토큰 저장하고 서버에 이전 토큰 삭제하고 현재 토큰 등록
            else if(token != beforeToken) {
                BaseApplication.prefs.token = token
                viewModel.deleteFcmToken(DeleteFcmToken(beforeToken))
                viewModel.registerFcmToken(PostFcmToken(token))
                Timber.d("new token area")
            }

            Timber.d("token : $token\n beforeToken : $beforeToken")

            viewModel.token = token
        })
    }

    private fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.vm = viewModel
    }

    private fun observeViewModel() {
        viewModel.onClickedStartButton.observe(this, Observer {
            val intent = Intent(this, StockListActivity::class.java)
            intent.putExtra(Consts.SF_TOKEN, viewModel.token)
            startActivity(intent)
        })
    }
}