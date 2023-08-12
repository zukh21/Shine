package kg.ticode.shine.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.domain.use_case.WhoIsUser
import kg.ticode.shine.domain.use_case.auth_validate.ValidateFirstName
import kg.ticode.shine.domain.use_case.auth_validate.ValidatePhoneNumber
import kg.ticode.shine.domain.use_case.auth_validate.ValidationAge
import kg.ticode.shine.domain.use_case.auth_validate.ValidationEmail
import kg.ticode.shine.domain.use_case.auth_validate.ValidationLastName
import kg.ticode.shine.domain.use_case.auth_validate.ValidationPassword
import kg.ticode.shine.enums.UserRole
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.AuthorizationUserResponse
import kg.ticode.shine.model.RegistrationFormState
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.presentation.RegistrationFormEvent
import kg.ticode.shine.repository.AuthRepository
import kg.ticode.shine.utils.AppAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val repository: AuthRepository,
    private val appAuth: AppAuth,
) : AndroidViewModel(application) {
    private val validateFirstName: ValidateFirstName = ValidateFirstName()
    private val validateLastName: ValidationLastName = ValidationLastName()
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber()
    private val validateEmail: ValidationEmail = ValidationEmail()
    private val validateAge: ValidationAge = ValidationAge()
    private val validatePassword: ValidationPassword = ValidationPassword()
    private var _userRole = MutableLiveData<String?>(null)
    val userRole: LiveData<String?> = _userRole
    var state by mutableStateOf(RegistrationFormState())
    private val validationEventChannel = Channel<ApiResult>()
    val validationEvents = validationEventChannel.receiveAsFlow()
    private val _isUser = MutableLiveData<WhoIsUser>()
    val isUser = _isUser
    fun userIsAuthenticatedAndUserId(isTrue: (Boolean) -> Unit, userId: (Long) -> Unit) {
        val id = appAuth.authStateFlow.value?.id != null && appAuth.authStateFlow.value?.id != 0L
        println("token: ${appAuth.authStateFlow.value?.jwtToken}")
        isTrue.invoke(id)
        if (id) {
            userId.invoke(appAuth.authStateFlow.value?.id!!)
        }
    }

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.AgeChanged -> state = state.copy(age = event.age)
            is RegistrationFormEvent.EmailChanged -> state = state.copy(email = event.email)
            is RegistrationFormEvent.FirstNameChanged -> state =
                state.copy(firstName = event.firstName)

            is RegistrationFormEvent.LastNameChanged -> state =
                state.copy(lastName = event.lastName)

            is RegistrationFormEvent.PasswordChanged -> state =
                state.copy(password = event.password)

            is RegistrationFormEvent.PhoneNumberChanged -> state =
                state.copy(phoneNumber = event.phoneNumber)

            RegistrationFormEvent.Registration -> {
                registrationUser()
            }

            RegistrationFormEvent.AuthorizationWithPhone -> {
                authorizationWithPhone()
            }

            RegistrationFormEvent.AuthorizationWithEmail -> {
                authorizationWithEmail()
            }
        }
    }

    private fun registrationUser() {
        val emailResult = validateEmail.execute(state.email)
        val firstNameResult = validateFirstName.execute(state.firstName)
        val lastNameResult = validateLastName.execute(state.lastName)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val ageResult = validateAge.execute(state.age)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            firstNameResult,
            lastNameResult,
            phoneNumberResult,
            ageResult,
            passwordResult
        ).any { !it.successful }
        if (hasError) {
            state = state.copy(
                firstNameError = firstNameResult.reason,
                lastNameError = lastNameResult.reason,
                phoneNumberError = phoneNumberResult.reason,
                emailError = emailResult.reason,
                ageError = ageResult.reason,
                passwordError = passwordResult.reason
            )
            viewModelScope.launch {
                validationEventChannel.send(ApiResult.Error)
            }
        } else {
            viewModelScope.launch {
                try {
                    val response = repository.userRegistration(
                        RegistrationUserRequest(
                            age = state.age,
                            email = state.email,
                            firstName = state.firstName,
                            lastName = state.lastName,
                            password = state.password,
                            phoneNumber = state.phoneNumber,
                            privacyPolicyAccepted = true,
                        )
                    )
                    if (!response.isSuccessful) {
                        val error = getRegisterError(response.errorBody())
                        state = when (error) {
                            "Эл. адрес уже существует" -> {
                                state.copy(
                                    emailError = "Эл. адрес уже существует",
                                    phoneNumberError = null
                                )
                            }

                            "Номер телефона уже существует" -> {
                                state.copy(
                                    phoneNumberError = "Номер телефона уже существует",
                                    emailError = null
                                )
                            }

                            else -> {
                                state.copy(phoneNumberError = null, emailError = null)
                            }
                        }
                        validationEventChannel.send(ApiResult.Error)
                    } else {
                        validationEventChannel.send(ApiResult.Success)
                    }
                } catch (e: SocketTimeoutException) {
                    validationEventChannel.send(ApiResult.Timeout)
                } catch (e: Exception) {
                    validationEventChannel.send(ApiResult.Error)
                    e.printStackTrace()
                }
            }
        }

    }


    private fun authorizationWithPhone() {
        val phoneResult = validatePhoneNumber.execute(state.phoneNumber)
        val passwordResult = validatePassword.execute(state.password)
        val hasError = listOf(
            phoneResult,
            passwordResult
        ).any {
            !it.successful
        }
        if (hasError) {
            state = state.copy(
                phoneNumberError = phoneResult.reason,
                passwordError = passwordResult.reason
            )
            viewModelScope.launch {
                validationEventChannel.send(ApiResult.Error)
            }
        } else {
            viewModelScope.launch {
                try {
                    val response = repository.userAuthorizationPhone(
                        AuthorizationUserRequestPhone(
                            state.password,
                            state.phoneNumber
                        )
                    )
                    if (!response.isSuccessful) {
                        val error = getAuthorizationError(response.errorBody())
                        state = state.copy(phoneNumberError = error)
                        validationEventChannel.send(ApiResult.Error)
                    } else {
                        validationEventChannel.send(ApiResult.Success)
                    }
                } catch (socketTOE: SocketTimeoutException) {
                    validationEventChannel.send(ApiResult.Timeout)
                } catch (e: Exception) {
                    e.printStackTrace()
                    validationEventChannel.send(ApiResult.Error)
                }
            }
        }
    }

    private fun authorizationWithEmail() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val hasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.successful
        }
        if (hasError) {
            state = state.copy(
                emailError = emailResult.reason,
                passwordError = passwordResult.reason
            )
            viewModelScope.launch {
                validationEventChannel.send(ApiResult.Error)
            }
        } else {
            viewModelScope.launch {
                try {
                    val response = repository.userAuthorizationEmail(
                        AuthorizationUserRequestEmail(
                            state.email,
                            state.password
                        )
                    )
                    if (!response.isSuccessful) {
                        val error = getAuthorizationError(response.errorBody())
                        state = state.copy(emailError = error)
                        validationEventChannel.send(ApiResult.Error)
                    } else {
                        validationEventChannel.send(ApiResult.Success)
                    }
                } catch (socketTOE: SocketTimeoutException) {
                    validationEventChannel.send(ApiResult.Timeout)

                } catch (e: Exception) {
                    e.printStackTrace()
                    validationEventChannel.send(ApiResult.Error)
                }
            }
        }
    }

    private fun getRegisterError(errorBody: ResponseBody?): String? {
        val gson = Gson()
        val error = gson.fromJson(errorBody?.string(), RegistrationUserResponse::class.java)
        return error.errorMessage
    }

    private fun getAuthorizationError(errorBody: ResponseBody?): String? {
        val gson = Gson()
        val error = gson.fromJson(errorBody?.string(), AuthorizationUserResponse::class.java)
        return error.message
    }

    init {
        viewModelScope.launch {
            userRole()
        }
    }

    private suspend fun userRole() {
        _userRole.value = appAuth.authStateFlow.value?.role
        when (_userRole.value) {
            UserRole.ADMIN.name -> {
                _isUser.value = WhoIsUser.IsAdmin
            }

            UserRole.MANAGER.name -> {
                _isUser.value = WhoIsUser.IsManager
            }

            UserRole.USER.name -> {
                _isUser.value = WhoIsUser.IsJustUser
            }

            null -> Unit
        }
    }

    fun logout() {
        appAuth.removeAuth()
    }


}