package kg.ticode.shine.model

import kg.ticode.shine.enums.UserRole

data class UserAuthState(
    val id: Long,
    val jwtToken: String,
    val message: String,
    val role: String
)