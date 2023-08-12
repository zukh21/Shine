package kg.ticode.shine.repository

import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.MediaResponseModel
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.model.UserUpdateDto
import retrofit2.Response
import java.io.File


interface UserRepository {
    suspend fun getUserById(userId: Long): Response<UserDto>

    suspend fun updateUser(userUpdateDto: UserUpdateDto, file: File? = null): Response<RegistrationUserResponse>

    suspend fun updateAvatar(file: File): Response<MediaResponseModel>
}