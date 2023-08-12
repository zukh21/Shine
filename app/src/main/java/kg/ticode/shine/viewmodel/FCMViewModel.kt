package kg.ticode.shine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.shine.model.FCMPushNotificationRequestModel
import kg.ticode.shine.repository.FCMRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FCMViewModel @Inject constructor(
    private val fcmRepository: FCMRepository
) : ViewModel() {
    fun sendPushNotification(fcmPushNotificationRequestModel: FCMPushNotificationRequestModel) {
        viewModelScope.launch {
            val job = viewModelScope.async {
                try {
                    println("responce fcm")
                    val response =
                        fcmRepository.sendFCMPushNotification(fcmPushNotificationRequestModel)
                    println("response.body() fcm: ${response.body()}")
                } catch (e: Exception) {
                    throw Exception(e)
                }
            }
            job.await()

        }
    }
}