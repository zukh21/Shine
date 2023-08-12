package kg.ticode.shine.repository

import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.AuthorizationUserResponse
import kg.ticode.shine.model.MediaResponseModel
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.model.UserUpdateDto
import retrofit2.Response
import java.io.File

interface AuthRepository {
    suspend fun userRegistration(
        registrationUserRequest: RegistrationUserRequest
    ): Response<RegistrationUserResponse>

    suspend fun userAuthorizationPhone(
        authorizationUserRequestPhone: AuthorizationUserRequestPhone,
    ): Response<AuthorizationUserResponse>

    suspend fun userAuthorizationEmail(authorizationUserRequestEmail: AuthorizationUserRequestEmail): Response<AuthorizationUserResponse>
}