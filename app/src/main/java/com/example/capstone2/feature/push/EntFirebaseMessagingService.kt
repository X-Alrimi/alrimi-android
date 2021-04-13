package com.example.capstone2.feature.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.capstone2.R
import com.example.capstone2.feature.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.math.abs

class EntFirebaseMessagingService : FirebaseMessagingService() {

    companion object{
        private const val TAG = "FirebaseService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "new Token : $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From : ${remoteMessage.from}")

        if(remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "new Message : ${remoteMessage.notification?.body}")

            val data : Map<String, String> = remoteMessage.data

            // 데이터 맵핑하기
            val title = data["title"]
            val body = data["body"]

            // 알림 생성
            sendNotification(title, body)
        }
    }

    // 알림 생성
    private fun sendNotification(title : String?, body : String?) {
        // 푸시 알림 클릭시 intent 정보
        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
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