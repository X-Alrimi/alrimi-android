package com.example.capstone2.feature.stock

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import kotlinx.android.synthetic.main.activity_stock_graph.*

class StockGraphActivity: AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_graph)

        webView = web_view_graph
        textView = txt_no_internet

        val url = intent.getStringExtra(Consts.STOCK_GRAPH)

        webView.apply {
            // 인터넷 연결 체크
            if(!getNetworkConnected(applicationContext)) {
                webView.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }
            else {
                // 새 창이 뜨지 않도록 방지
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()

                settings.javaScriptEnabled = true // 자바 스크립트 허용
                settings.setSupportZoom(true) // 화면 줌 허용
                settings.builtInZoomControls = true // 화면 확대 축소 허용

                loadUrl(url)
            }
        }
    }

    override fun onBackPressed() {
        // 뒤로가기가 가능하면 뒤로가기, 더 뒤로갈 페이지가 없으면 액티비티 종료
        if (webView.canGoBack()) webView.goBack()
        else finish()
    }

    // 인터넷 연결 확인 함수
    fun getNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}