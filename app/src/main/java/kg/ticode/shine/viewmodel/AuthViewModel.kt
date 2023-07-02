package kg.ticode.shine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val repository: AuthRepository
) : AndroidViewModel(application) {
    private val _responseIsSuccess = MutableStateFlow(false)
     val responseIsSuccess: StateFlow<Boolean> = _responseIsSuccess.asStateFlow()
    fun registration(registrationUserRequest: RegistrationUserRequest) {
        viewModelScope.launch {
            val response = repository.userRegistration(registrationUserRequest)
            println("response vm: $response")
            _responseIsSuccess.update { response }
        }
    }

    fun authorizationPhone(authorizationUserRequestPhone: AuthorizationUserRequestPhone): Boolean? {
        viewModelScope.launch {
            val response = repository.userAuthorizationPhone(authorizationUserRequestPhone)
            _responseIsSuccess.update { response }
        }
        return _responseIsSuccess.value
    }

    fun authorizationEmail(authorizationUserRequestEmail: AuthorizationUserRequestEmail): Boolean? {
        viewModelScope.launch {
            val response = repository.userAuthorizationEmail(authorizationUserRequestEmail)
            _responseIsSuccess.value = response
        }
        return _responseIsSuccess.value
    }

}