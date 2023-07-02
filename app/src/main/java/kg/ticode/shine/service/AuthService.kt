package kg.ticode.shine.service

import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.AuthorizationUserResponse
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.model.RegistrationUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("jwt/registration")
    suspend fun userRegistration(@Body registrationUserRequest: RegistrationUserRequest): Response<RegistrationUserResponse>

    @POST("jwt/login")
    suspend fun userAuthorizationRequestPhone(@Body authorizationUserRequestPhone: AuthorizationUserRequestPhone): Response<AuthorizationUserResponse>

    @POST("jwt/login")
    suspend fun userAuthorizationRequestEmail(@Body authorizationUserRequestEmail: AuthorizationUserRequestEmail): Response<AuthorizationUserResponse>

}