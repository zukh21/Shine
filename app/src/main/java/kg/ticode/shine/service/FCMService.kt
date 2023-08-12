package kg.ticode.shine.service

import kg.ticode.shine.model.FCMPushNotificationRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface FCMService {
    @POST("fcm/send")
    suspend fun fCMPushNotificationSend(
        @Body fcmPushNotificationRequestModel: FCMPushNotificationRequestModel,
    ): Response<Any>
}