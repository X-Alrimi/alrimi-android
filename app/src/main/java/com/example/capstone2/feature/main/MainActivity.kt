package com.example.capstone2.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone2.R
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
        setupTimber()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM Main", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "get FCM Token"
            Log.d("FCM Main", "$msg : $token")
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
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
            startActivity(intent)
        })
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}