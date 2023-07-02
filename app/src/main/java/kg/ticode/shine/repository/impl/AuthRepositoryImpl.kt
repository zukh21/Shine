package kg.ticode.shine.repository.impl

import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.repository.AuthRepository
import kg.ticode.shine.service.AuthService
import kg.ticode.shine.utils.AppAuth
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val appAuth: AppAuth
): AuthRepository {
    override suspend fun userRegistration(registrationUserRequest: RegistrationUserRequest): Boolean {
        val response = service.userRegistration(registrationUserRequest)
        return response.isSuccessful
    }

    override suspend fun userAuthorizationPhone(authorizationUserRequestPhone: AuthorizationUserRequestPhone): Boolean {
        val response = service.userAuthorizationRequestPhone(authorizationUserRequestPhone)
        return if (response.isSuccessful){
            appAuth.setAuth(response.body()?.authorities, response.body()?.jwtToken!!, response.body()?.message)
            true
        }else{
            false
        }
    }

    override suspend fun userAuthorizationEmail(authorizationUserRequestEmail: AuthorizationUserRequestEmail): Boolean {
        val response = service.userAuthorizationRequestEmail(authorizationUserRequestEmail)
        return if (response.isSuccessful){
            appAuth.setAuth(response.body()?.authorities, response.body()?.jwtToken!!, response.body()?.message)
            true
        }else{
            false
        }
    }

}