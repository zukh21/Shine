package kg.ticode.shine.repository.impl

import kg.ticode.shine.domain.dao.UserDao
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.entity.UserEntity
import kg.ticode.shine.model.MediaResponseModel
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.model.UserUpdateDto
import kg.ticode.shine.repository.UserRepository
import kg.ticode.shine.service.MediaService
import kg.ticode.shine.service.UserService
import kg.ticode.shine.utils.AppAuth
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: UserService,
    private val appAuth: AppAuth,
    private val userDao: UserDao,
    private val mediaService: MediaService
) : UserRepository {
    override suspend fun getUserById(userId: Long): Response<UserDto> {
        val response = service.getUserById(userId)
        if (response.isSuccessful) {
            userDao.insertUser(UserEntity.fromUserDto(response.body()!!))
        }
        return response
    }

    override suspend fun updateUser(
        userUpdateDto: UserUpdateDto,
        file: File?
    ): Response<RegistrationUserResponse> {
        return if (file == null) {
            userDao.updateUser(UserEntity.fromUserUpdateDto(userUpdateDto))
            val response = service.updateUser(userUpdateDto)
            if (response.isSuccessful) {
                appAuth.setAuth(
                    response.body()?.id!!,
                    response.body()?.token!!,
                    response.body()?.errorMessage,
                    response.body()?.role?.name!!
                )
            }
            response
        } else {
            val media = InsertMedia(mediaService).insert(file)
            val newUser = userUpdateDto.copy(avatarUrl = media)
            val response = service.updateUser(newUser)
            if (response.isSuccessful) {
                userDao.updateUser(UserEntity.fromUserUpdateDto(newUser))
                appAuth.setAuth(
                    response.body()?.id!!,
                    response.body()?.token!!,
                    response.body()?.errorMessage,
                    response.body()?.role?.name!!
                )
            }
            response
        }
    }

    override suspend fun updateAvatar(file: File): Response<MediaResponseModel> {
        val f = MultipartBody.Part.createFormData(
            "file", file.name, requireNotNull(file.asRequestBody())
        )
        return service.updateAvatar(f)
    }
}