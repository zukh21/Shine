package kg.ticode.shine.service

import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.MediaResponseModel
import kg.ticode.shine.model.RegistrationUserResponse
import kg.ticode.shine.model.UserUpdateDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserService {
    @GET("admin/getUser/{userId}")
    suspend fun getUserById(@Path("userId") userId: Long): Response<UserDto>

    @Multipart
    @POST("avatar/uploadAvatar")
    suspend fun uploadAvatar(@Part file: MultipartBody.Part): Response<MediaResponseModel>

    @PATCH("avatar/updateUser")
    suspend fun updateUser(@Body userUpdateDto: UserUpdateDto): Response<RegistrationUserResponse>

    @Multipart
    @PUT("avatar/updateAvatar")
    suspend fun updateAvatar(@Part file: MultipartBody.Part): Response<MediaResponseModel>
}