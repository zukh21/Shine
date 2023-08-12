package kg.ticode.shine.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.model.UserUpdateDto

@Entity(tableName = "users")
data class UserEntity(
    val age: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val avatarUrl: String? = null,
    val privacyPolicyAccepted: Boolean,
    @PrimaryKey
    val id: Long
) {
    fun toUserDto(): UserDto = UserDto(id, firstName, lastName, email, phoneNumber, age, avatarUrl)
    companion object {
        fun fromUserDto(userDto: UserDto): UserEntity = UserEntity(
            userDto.age,
            userDto.email,
            userDto.firstName,
            userDto.lastName,
            userDto.phoneNumber,
            userDto.avatarUrl,
            true,
            userDto.id
        )
        fun fromUserUpdateDto(userUpdateDto: UserUpdateDto): UserEntity = UserEntity(
            userUpdateDto.age,
            userUpdateDto.email,
            userUpdateDto.firstName,
            userUpdateDto.lastName,
            userUpdateDto.phoneNumber,
            userUpdateDto.avatarUrl,
            true,
            userUpdateDto.id
        )
    }

}