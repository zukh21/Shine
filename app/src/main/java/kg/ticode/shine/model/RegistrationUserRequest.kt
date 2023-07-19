package kg.ticode.shine.model

data class RegistrationUserRequest(
    val age: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val privacyPolicyAccepted: Boolean
)