package kg.ticode.shine.model

import kg.ticode.shine.enums.UserRole

data class AuthorizationUserResponse(
    val jwtToken: String?,
    val message: String?,
    val userId: Long?,
    val role: UserRole?
)
