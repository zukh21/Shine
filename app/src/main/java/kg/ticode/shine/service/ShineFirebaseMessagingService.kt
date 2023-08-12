package kg.ticode.shine.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.shine.MainActivity
import kg.ticode.shine.R
import kg.ticode.shine.model.FCMPushNotificationRequestModel.Companion.CAR_FIRST_IMAGE
import kg.ticode.shine.model.FCMPushNotificationRequestModel.Companion.MARK
import kg.ticode.shine.model.FCMPushNotificationRequestModel.Companion.MODEL
import kg.ticode.shine.model.FCMPushNotificationRequestModel.Companion.YEAR_OF_ISSUE
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants.CHANNEL_ID
import kg.ticode.shine.utils.CustomConstants.FIREBASE_REALTIME_DATABASE_TOKEN_KEY
import kg.ticode.shine.utils.CustomConstants.REF_FCM
import kg.ticode.shine.utils.CustomConstants.REF_FCM_TOKEN_KEY
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

@AndroidEntryPoint
class ShineFirebaseMessagingService : FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        try {
            val notificationStyle = Notification.BigPictureStyle()
                .bigPicture(message.data[CAR_FIRST_IMAGE]?.let { imageLinkToBitmap(it) })
                .setBigContentTitle("${message.data[MARK]} ${message.data[MODEL]}, ${message.data[YEAR_OF_ISSUE]}")
            val title = message.notification?.title
            val text = message.notification?.body
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivities(
                this,
                0,
                arrayOf(intent),
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel: NotificationChannel = NotificationChannel(
                    CHANNEL_ID,
                    "Shine Message Notification",
                    NotificationManager.IMPORTANCE_HIGH
                )
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                val notification = Notification.Builder(this, CHANNEL_ID)
                    .setLargeIcon(
                        message.data[CAR_FIRST_IMAGE]?.let { imageLinkToBitmap(it) }
                    )
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.shine_logo_svg)
                    .setColor(PrimaryColor.toArgb())
                    .setStyle(notificationStyle)
                    .setAutoCancel(true)
                    .setTicker("Shine")
                    .setContentIntent(pendingIntent)
                    .build()
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    println("TOKEN permission")
                    return
                } else {
                    NotificationManagerCompat.from(this)
                        .notify(Random.nextInt(100_000), notification)
                }
            } else {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Shine Message Notification",
                    NotificationManager.IMPORTANCE_HIGH
                )
                getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
                val notification = Notification.Builder(this, CHANNEL_ID)
                    .setLargeIcon(
                        message.data[CAR_FIRST_IMAGE]?.let { imageLinkToBitmap(it) }
                    )
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.shine_logo_svg)
                    .setStyle(notificationStyle)
                    .setColor(PrimaryColor.toArgb())
                    .setAutoCancel(true)
                    .setTicker("Shine")
                    .build()
                NotificationManagerCompat.from(this).notify(Random.nextInt(100_000), notification)
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val ref = baseContext.getSharedPreferences(REF_FCM, Context.MODE_PRIVATE)
        ref.edit().putString(REF_FCM_TOKEN_KEY, token).apply()
        val database = Firebase.database
        val fRef = database.getReference(FIREBASE_REALTIME_DATABASE_TOKEN_KEY)
        fRef.child("push_token_for_${Build.MODEL}").setValue(token)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        try {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    private fun imageLinkToBitmap(link: String): Bitmap {
        val url = URL(link)
        val connection = url.openConnection() as (HttpURLConnection)
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(input)
        return bitmap
    }
    private fun uriToBitmap(uri: String): Bitmap{
        try {
            val u = Uri.parse(uri)
            val inputStream = contentResolver.openInputStream(u)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}