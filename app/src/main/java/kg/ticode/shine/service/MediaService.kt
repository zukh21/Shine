package kg.ticode.shine.service

import kg.ticode.shine.model.MediaResponseModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MediaService {
    @Multipart
    @POST("imageCar/uploadAvatar")
    suspend fun insertMedia(@Part file: MultipartBody.Part): Response<MediaResponseModel>
    @Multipart
    @POST("imageCar/uploadAvatar")
    suspend fun insertMediaImages(@Part file: MultipartBody.Part): Response<MediaResponseModel>
}