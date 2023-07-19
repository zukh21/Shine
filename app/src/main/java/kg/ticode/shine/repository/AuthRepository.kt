package kg.ticode.shine.repository

import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.AuthorizationUserResponse
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.model.RegistrationUserResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun userRegistration(
        registrationUserRequest: RegistrationUserRequest
    ): Response<RegistrationUserResponse>

    suspend fun userAuthorizationPhone(
        authorizationUserRequestPhone: AuthorizationUserRequestPhone,
    ): Response<AuthorizationUserResponse>

    suspend fun userAuthorizationEmail(authorizationUserRequestEmail: AuthorizationUserRequestEmail): Response<AuthorizationUserResponse>
    suspend fun getUserById(userId: Long): Response<UserDto>
    suspend fun selectLanguage(lang: String): Response<String>
}