package kg.ticode.shine.utils

import kg.ticode.shine.model.FCMPushNotificationRequestModel
import kg.ticode.shine.model.FCMPushNotificationRequestModelData
import kg.ticode.shine.model.NotificationModel
import kg.ticode.shine.viewmodel.FCMViewModel

class SendFCMNotification {
    companion object {
        fun send(
            token: String,
            ownToken: String,
            appName: String,
            fcmViewModel: FCMViewModel,
            fcmPushNotificationRequestModelData: FCMPushNotificationRequestModelData
        ) {
            if (ownToken != token) {
                fcmViewModel.sendPushNotification(
                    fcmPushNotificationRequestModel(
                        token,
                        appName,
                        fcmPushNotificationRequestModelData
                    )
                )
            }
        }

        private fun fcmPushNotificationRequestModel(
            token: String,
            appName: String,
            fcmPushNotificationRequestModelData: FCMPushNotificationRequestModelData
        ): FCMPushNotificationRequestModel =
            FCMPushNotificationRequestModel(
                to = token,
                notification = NotificationModel(
                    "Уведомление в $appName",
                    "У $appName появилась новая машина",
                ),
                mapOf(
                    Pair(
                        FCMPushNotificationRequestModel.MARK,
                        fcmPushNotificationRequestModelData.mark
                    ),
                    Pair(
                        FCMPushNotificationRequestModel.MODEL,
                        fcmPushNotificationRequestModelData.model
                    ),
                    Pair(
                        FCMPushNotificationRequestModel.YEAR_OF_ISSUE,
                        fcmPushNotificationRequestModelData.yearOfIssue
                    ),
                    Pair(
                        FCMPushNotificationRequestModel.CAR_FIRST_IMAGE,
                        fcmPushNotificationRequestModelData.car_first_image
                    ),
                )
            )
    }
}


