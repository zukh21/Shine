package kg.ticode.shine.model

data class AuthorizationUserResponse(
    val authorities: String,
    val jwtToken: String,
    val message: String
)
