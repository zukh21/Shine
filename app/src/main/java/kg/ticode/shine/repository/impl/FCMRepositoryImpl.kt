package kg.ticode.shine.repository.impl

import kg.ticode.shine.model.FCMPushNotificationRequestModel
import kg.ticode.shine.repository.FCMRepository
import kg.ticode.shine.service.FCMService
import retrofit2.Response
import javax.inject.Inject

class FCMRepositoryImpl @Inject constructor(
    private val fcmService: FCMService
) : FCMRepository {
    override suspend fun sendFCMPushNotification(senderAndData: FCMPushNotificationRequestModel): Response<Any> {
        return fcmService.fCMPushNotificationSend(fcmPushNotificationRequestModel =  senderAndData)
    }
}