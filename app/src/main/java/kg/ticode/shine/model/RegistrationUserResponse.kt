package kg.ticode.shine.model

data class RegistrationUserResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val age: Int
)