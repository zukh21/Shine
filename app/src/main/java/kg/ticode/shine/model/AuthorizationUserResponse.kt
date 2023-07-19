package kg.ticode.shine.model

data class AuthorizationUserResponse(
    val jwtToken: String,
    val message: String,
    val userId: Long,
)
