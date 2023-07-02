package kg.ticode.shine.repository

import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.RegistrationUserRequest

interface AuthRepository {
    suspend fun userRegistration(registrationUserRequest: RegistrationUserRequest): Boolean
    suspend fun userAuthorizationPhone(authorizationUserRequestPhone: AuthorizationUserRequestPhone): Boolean
    suspend fun userAuthorizationEmail(authorizationUserRequestEmail: AuthorizationUserRequestEmail): Boolean
}