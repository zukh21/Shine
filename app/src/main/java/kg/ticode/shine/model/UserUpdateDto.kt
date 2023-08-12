package kg.ticode.shine.model

import kg.ticode.shine.dto.UserDto

data class UserUpdateDto(
    val age: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val avatarUrl: String? = null,
    val privacyPolicyAccepted: Boolean,
    val id: Long
) {
    companion object {
        fun fromUserDto(userDto: UserDto): UserUpdateDto = UserUpdateDto(
            userDto.age,
            userDto.email,
            userDto.firstName,
            userDto.lastName,
            userDto.phoneNumber,
            userDto.avatarUrl,
            true,
            userDto.id
        )
    }
}
