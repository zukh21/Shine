package kg.ticode.shine.repository.impl

import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.AuthorizationUserResponse
import kg.ticode.shine.model.MediaResponseModel
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.model.UserUpdateDto
import kg.ticode.shine.repository.AuthRepository
import kg.ticode.shine.service.AuthService
import kg.ticode.shine.utils.AppAuth
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
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
                response.body()?.errorMessage,
                response.body()?.role?.name!!
            )
        }
        return response
    }

    override suspend fun userAuthorizationPhone(
        authorizationUserRequestPhone: AuthorizationUserRequestPhone
    ): Response<AuthorizationUserResponse> {
        val response = service.userAuthorizationRequestPhone(authorizationUserRequestPhone)
        if (response.isSuccessful) {
            println(" response.body()?.role?.name!!: ${response.body()?.role?.name!!}")
            appAuth.setAuth(
                response.body()?.userId!!,
                response.body()?.jwtToken!!,
                response.body()?.message,
                response.body()?.role?.name!!
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
                response.body()?.message,
                response.body()?.role?.name!!
            )
        }
        return response
    }

}