package kg.ticode.shine.model

import kg.ticode.shine.enums.UserRole

data class RegistrationUserResponse(
    val errorMessage: String?,
    val id: Long?,
    val token: String?,
    val role: UserRole?
)