package kg.ticode.shine.repository.impl

import kg.ticode.shine.service.MediaService
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class InsertMedia(
    private val mediaService: MediaService
) {
    suspend fun insert(file: File): String? {
        val media = MultipartBody.Part.createFormData(
            "file",
            file.name,
            requireNotNull(file.asRequestBody())
        )
        val response = mediaService.insertMedia(media)
        return response.body()?.avatarUrl
    }
}

class InsertMediaImages(
    private val mediaService: MediaService
) {
    suspend fun insert(files: List<File>): Pair<Boolean, List<String>> {
        val medias = mutableListOf<MultipartBody.Part>()
        val images = mutableListOf<String>()
        files.forEach {
            val media = MultipartBody.Part.createFormData(
                "file",
                it.name,
                requireNotNull(it.asRequestBody())
            )
            medias.add(media)
        }
        var count = 0
        medias.forEach {
            println("media response ${count++}")
            val response = mediaService.insertMediaImages(it)
            response.body()?.avatarUrl?.let { it1 -> images.add(it1) }
        }
        val isFinish: Boolean = true
        return Pair(isFinish, images)
    }
}
