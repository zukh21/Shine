package kg.ticode.shine.repository

import kg.ticode.shine.model.FCMPushNotificationRequestModel
import kg.ticode.shine.model.FCMPushNotificationResponseModel
import retrofit2.Response

interface FCMRepository {
    suspend fun sendFCMPushNotification(senderAndData: FCMPushNotificationRequestModel): Response<Any>
}