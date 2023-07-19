package kg.ticode.shine.model

data class RegistrationUserResponse(
    val errorMessage: String,
    val id: Long,
    val token: String
)