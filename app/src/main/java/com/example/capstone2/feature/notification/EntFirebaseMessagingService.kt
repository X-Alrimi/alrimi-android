package com.example.capstone2.feature.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.capstone2.R
import com.example.capstone2.core.Consts
import com.example.capstone2.feature.news.NewsWebViewActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import kotlin.math.abs

class EntFirebaseMessagingService : FirebaseMessagingService() {

    companion object{
        private const val TAG = "FirebaseService"
        private const val NEW_TOKEN_MSG = "새로운 디바이스 토큰이 발급되었습니다. 계속해서 알림을 받으시려면 어플을 다시 시작해주세요. 최초 설치 후 첫 실행이라면 이 과정을 생략해도 좋습니다."
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("$TAG new Token : $token")
        Handler(Looper.getMainLooper()).post(Runnable {
            run() {
                Toast.makeText(applicationContext, NEW_TOKEN_MSG, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("$TAG From : ${remoteMessage.from}")

        if(remoteMessage.data.isNotEmpty()) {
            Timber.d("$TAG new Message : ${remoteMessage.notification?.body}")

            val data : Map<String, String> = remoteMessage.data

            // 데이터 맵핑하기
            val title = data["title"]
            val body = data["body"]
            val link = data["link"]

            // 알림 생성
            sendNotification(title, body, link)
        }
    }

    // 알림 생성
    private fun sendNotification(title : String?, body : String?, link: String?) {
        // 푸시 알림 클릭시 intent 정보
        val intent = Intent(this, NewsWebViewActivity::class.java)
        intent.apply {
            putExtra(Consts.NEWS_LINK, link)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = "entNotification"
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) // 알림 소리

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘
            .setContentTitle(title) // 제목
            .setContentText(body) // 본문
            .setAutoCancel(true) // 클릭시 알림 자동 삭제
            .setSound(notificationSound) // 사운드
            .setContentIntent(pendingIntent) // 클릭시 화면 이동

        var notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // 알림 매니저 생성

        // android Oreo부터 notification channel이 필요함.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = NotificationChannel(channelId, "푸시 알림", NotificationManager.IMPORTANCE_DEFAULT) // 알림 채널 생성
            notificationManager.createNotificationChannel(notificationChannel)
        }

        /**
         * 알림 생성.
         * 첫 번째 인자 값이 고정되면 알림이 하나만 쌓임(id가 같으므로).
         * 따라서 현재 시간을 밀리초 단위로 받아 id 값으로 사용.
         * System,.currentTimeMillis()의 Type = Long 이므로
         * abs()를 사용해 오버플로우를 방지, id에 음수가 들어가지 않도록 함.
         */
        val currentTimeMillis = System.currentTimeMillis()
        notificationManager.notify(abs(currentTimeMillis.toInt()), notificationBuilder.build())
    }
}