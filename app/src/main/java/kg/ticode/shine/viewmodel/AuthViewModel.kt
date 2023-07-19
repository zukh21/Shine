package kg.ticode.shine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.repository.AuthRepository
import kg.ticode.shine.utils.AppAuth
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val repository: AuthRepository,
    private val appAuth: AppAuth,
) : AndroidViewModel(application) {
    private val _responseIsSuccess = MutableLiveData<Boolean>()
    val responseIsSuccess: LiveData<Boolean> = _responseIsSuccess
    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto> = _user
    private val _timeout = MutableLiveData<Boolean>()
    val timeout: LiveData<Boolean> = _timeout
    private var _userIsAuthenticated = MutableLiveData<Boolean>()
    var userIsAuthenticated: LiveData<Boolean> = _userIsAuthenticated
    private var _authenticatedUserId = MutableLiveData<Long>()
    var authenticatedUserId: LiveData<Long> = _authenticatedUserId
    private var _errorBody = MutableLiveData<String>()
    var errorBody: LiveData<String> = _errorBody
    val userId = appAuth.authStateFlow.value?.id

    fun authorizationWithPhone(authorizationUserRequestPhone: AuthorizationUserRequestPhone) {
        viewModelScope.launch {
            try {
                val response = repository.userAuthorizationPhone(authorizationUserRequestPhone)
                println("response.isSuccessful: ${response.isSuccessful}")
                _responseIsSuccess.value = response.isSuccessful
                _userIsAuthenticated.value = response.isSuccessful
                _authenticatedUserId.value = response.body()?.userId
                if (!response.isSuccessful) {
                    val json = response.errorBody()?.string()?.let { JSONObject(it) }
                    val error = json?.getString("message")
                    _errorBody.value = error
                }
            } catch (socketTOE: SocketTimeoutException) {
                _timeout.value = true
                println("timeout c")

            } catch (e: Exception) {
                e.printStackTrace()
                _timeout.value = false
            }
        }
    }

    fun authorizationWithEmail(authorizationUserRequestEmail: AuthorizationUserRequestEmail) {
        viewModelScope.launch {
            try {
                val response = repository.userAuthorizationEmail(authorizationUserRequestEmail)
                _responseIsSuccess.value = response.isSuccessful
                _userIsAuthenticated.value = response.isSuccessful
                _authenticatedUserId.value = response.body()?.userId
                if (!response.isSuccessful) {
                    val json = response.errorBody()?.string()?.let { JSONObject(it) }
                    val error = json?.getString("message")
                    _errorBody.value = error
                }
            } catch (socketTOE: SocketTimeoutException) {
                _timeout.value = true
                println("timeout c")

            } catch (e: Exception) {
                e.printStackTrace()
                _timeout.value = false
            }
        }
    }

    fun registration(
        registrationUserRequest: RegistrationUserRequest,
    ) {
        viewModelScope.launch {
            try {
                val response = repository.userRegistration(registrationUserRequest)
                _responseIsSuccess.value = response.isSuccessful
                _userIsAuthenticated.value = response.isSuccessful
                _authenticatedUserId.value = response.body()?.id
            } catch (e: Exception) {
                println("timeout c")
                when (e) {
                    is SocketTimeoutException -> {
                        _timeout.value = true
                    }

                    else -> {
                        _timeout.value = false
                        throw Exception(e)
                    }
                }
            }
        }
    }

    init {
        _userIsAuthenticated.value =
            appAuth.authStateFlow.value?.id != null && appAuth.authStateFlow.value?.id != 0L
    }

    fun logout() {
        appAuth.removeAuth()
        _userIsAuthenticated.value = false
    }

    fun selectLanguage(lang: String) {
        viewModelScope.launch {
            try {
                repository.selectLanguage(lang)
            } catch (e: Exception) {
                throw Exception(e)
            }
        }
    }

    fun clearTimeoutException() {
        _timeout.value = null
    }

    val refAuth = appAuth

    fun getUserById(id: Long) {
        viewModelScope.launch {
            println("id ;$id")
            val response = repository.getUserById(id)
            _user.value = response.body()
        }
    }

}