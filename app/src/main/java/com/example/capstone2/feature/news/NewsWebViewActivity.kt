package com.example.capstone2.feature.news

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone2.R

class NewsWebViewActivity: AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    lateinit var textView: TextView

    companion object {
        const val EXTRA_URL = "url" // 인텐트 키 상수 선언
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web_view)

        webView = findViewById(R.id.web_view_news)
        progressBar = findViewById(R.id.progress_news)
        textView = findViewById(R.id.txt_no_internet)

        val url = intent.getStringExtra(EXTRA_URL)

        webView.apply {
            // 인터넷 연결 체크
            if(!getNetworkConnected(applicationContext)) {
                webView.visibility = View.GONE
                progressBar.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }
            else {
                webView.apply {
                    settings.javaScriptEnabled = true // 자바 스크립트 허용

                    // 새 창이 뜨지 않도록 방지
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()

                    loadUrl(url)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }

    // 인터넷 연결 확인 함수
    fun getNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}