package kg.ticode.shine.model

data class UserAuthState(
    val id: Long,
    val jwtToken: String,
    val message: String
)