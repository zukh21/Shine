package kg.ticode.shine.service

import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.AuthorizationUserResponse
import kg.ticode.shine.model.MediaResponseModel
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.model.UserUpdateDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    @POST("jwt/registration")
    suspend fun userRegistration(@Body registrationUserRequest: RegistrationUserRequest): Response<RegistrationUserResponse>

    @POST("jwt/login")
    suspend fun userAuthorizationRequestPhone(@Body authorizationUserRequestPhone: AuthorizationUserRequestPhone): Response<AuthorizationUserResponse>

    @POST("jwt/login")
    suspend fun userAuthorizationRequestEmail(@Body authorizationUserRequestEmail: AuthorizationUserRequestEmail): Response<AuthorizationUserResponse>


}