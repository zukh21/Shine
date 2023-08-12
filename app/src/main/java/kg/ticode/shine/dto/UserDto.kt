package kg.ticode.shine.dto

data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val age: Int,
    val avatarUrl: String? = null
)
