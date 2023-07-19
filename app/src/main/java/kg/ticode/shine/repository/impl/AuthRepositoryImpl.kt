package kg.ticode.shine.repository.impl

import android.app.Activity
import androidx.compose.ui.text.intl.Locale
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.AuthorizationUserResponse
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.repository.AuthRepository
import kg.ticode.shine.service.AuthService
import kg.ticode.shine.utils.AppAuth
import kg.ticode.shine.utils.CustomConstants.AUTH
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val appAuth: AppAuth
) : AuthRepository {
    override suspend fun userRegistration(registrationUserRequest: RegistrationUserRequest): Response<RegistrationUserResponse> {
        val response = service.userRegistration(registrationUserRequest)
       if (response.isSuccessful) {
            appAuth.setAuth(
                response.body()?.id!!,
                response.body()?.token!!,
                response.body()?.errorMessage
            )
        }
        return response
    }

    override suspend fun userAuthorizationPhone(
        authorizationUserRequestPhone: AuthorizationUserRequestPhone
    ): Response<AuthorizationUserResponse> {
        val response = service.userAuthorizationRequestPhone(authorizationUserRequestPhone)
        if (response.isSuccessful) {
            appAuth.setAuth(
                response.body()?.userId!!,
                response.body()?.jwtToken!!,
                response.body()?.message
            )
        }
        return response
    }

    override suspend fun userAuthorizationEmail(authorizationUserRequestEmail: AuthorizationUserRequestEmail): Response<AuthorizationUserResponse> {
        val response = service.userAuthorizationRequestEmail(authorizationUserRequestEmail)
         if (response.isSuccessful) {
            appAuth.setAuth(
                response.body()?.userId!!,
                response.body()?.jwtToken!!,
                response.body()?.message
            )
        }
        return response
    }

    override suspend fun getUserById(userId: Long): Response<UserDto> {
        return service.getUserById(userId)
    }

    override suspend fun selectLanguage(lang: String): Response<String> {
        return service.selectLanguage(lang)
    }
}