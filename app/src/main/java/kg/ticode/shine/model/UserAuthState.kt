package kg.ticode.shine.model

data class UserAuthState(
    val authorities: String,
    val jwtToken: String,
    val message: String
)