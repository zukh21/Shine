package kg.ticode.shine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.shine.repository.impl.InsertMedia
import kg.ticode.shine.repository.impl.InsertMediaImages
import kg.ticode.shine.service.MediaService
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaService: MediaService
): ViewModel() {
    fun insertMedia(file: File, url: (String) -> Unit) = viewModelScope.launch {
        val link = InsertMedia(mediaService = mediaService).insert(file)
        link?.let { url.invoke(it) }
    }
    fun insertCarImages(files: List<File>, result: (Pair<Boolean, List<String>>) -> Unit) {
        viewModelScope.launch {
            try {
                val image = InsertMediaImages(mediaService = mediaService).insert(files)
                image.let {
                    println("viewmodel insertCarImages ${it.second}")
                    result.invoke(it)
                }
            } catch (e: Exception) {
                throw Exception(e)
            }
        }
    }
}