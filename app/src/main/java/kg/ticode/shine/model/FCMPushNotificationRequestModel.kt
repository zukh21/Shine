package kg.ticode.shine.model

import kg.ticode.shine.enums.CarYearOfIssue

data class FCMPushNotificationRequestModel(
    val to: String,
    val notification: NotificationModel,
    val data: Map<String, String>
){
    companion object{
        const val MARK = "mark"
        const val MODEL = "model"
        const val YEAR_OF_ISSUE = "year_of_issue"
        const val CAR_FIRST_IMAGE = "car_first_image"
    }
}

data class FCMPushNotificationRequestModelData(val mark: String, val model: String, val yearOfIssue: String, val car_first_image: String)

