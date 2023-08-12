package kg.ticode.shine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.UserUpdateDto
import kg.ticode.shine.repository.UserRepository
import kg.ticode.shine.repository.impl.InsertMedia
import kg.ticode.shine.repository.inMemory.UserRepositoryInMemory
import kg.ticode.shine.utils.AppAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userRepositoryInMemory: UserRepositoryInMemory,
) : ViewModel() {
    private val _getUserByIdEventChannel = Channel<ApiResult>()
    val getUserByIdEventChannel = _getUserByIdEventChannel.receiveAsFlow()

    private val _uploadAvatarEventChannel = Channel<ApiResult>()
    val uploadAvatarEventChannel = _uploadAvatarEventChannel.receiveAsFlow()

    private val _updateUserResult = Channel<ApiResult>()
    val updateUserResult = _updateUserResult.receiveAsFlow()

    private val _user = MutableLiveData<UserDto?>(null)
    val user: LiveData<UserDto?> = _user

    fun getUserById(id: Long) {
        viewModelScope.launch {
            try {
                if (user.value == null) {
                    _getUserByIdEventChannel.send(ApiResult.Loading)
                    val response = repository.getUserById(id)
                    if (!response.isSuccessful) {
                        _getUserByIdEventChannel.send(ApiResult.Error)
                    } else {
                        _user.value = response.body()?.id?.let {
                            userRepositoryInMemory.getUserById(it).toUserDto()
                        }
                        _getUserByIdEventChannel.send(ApiResult.Success)
                    }
                } else {
                    _user.value = userRepositoryInMemory.getUserById(user.value!!.id).toUserDto()
                }
            } catch (socketTOE: SocketTimeoutException) {
                _getUserByIdEventChannel.send(ApiResult.Timeout)
            } catch (e: Exception) {
                _getUserByIdEventChannel.send(ApiResult.Error)
            }

        }
    }

    fun updateUser(userUpdateDto: UserUpdateDto) {
        viewModelScope.launch {
                try {
                    _updateUserResult.send(ApiResult.Loading)
                    val updateResponse =
                        repository.updateUser(userUpdateDto)
                    if (updateResponse.isSuccessful) {
                        _updateUserResult.send(ApiResult.Success)
                    } else {
                        _updateUserResult.send(ApiResult.Error)
                    }
                } catch (socketTOE: SocketTimeoutException) {
                    _updateUserResult.send(ApiResult.Timeout)
                } catch (e: Exception) {
                    _updateUserResult.send(ApiResult.Error)
                }
        }
    }

    fun updateAvatar(file: File){
        viewModelScope.launch {
            try {
                _uploadAvatarEventChannel.send(ApiResult.Loading)
                val updateResponse = repository.updateAvatar(file)
                if (updateResponse.isSuccessful) {
                    _uploadAvatarEventChannel.send(ApiResult.Success)
                } else {
                    _uploadAvatarEventChannel.send(ApiResult.Error)
                }
            } catch (socketTOE: SocketTimeoutException) {
                _uploadAvatarEventChannel.send(ApiResult.Timeout)
            } catch (e: Exception) {
                _uploadAvatarEventChannel.send(ApiResult.Error)
            }
        }
    }
}