package kg.ticode.shine.utils

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kg.ticode.shine.model.UserAuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAuth @Inject constructor(@ApplicationContext context: Context) {

    val prefs = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
    private val _authStateFlow = MutableStateFlow<UserAuthState?>(null)
    private val idKey: String = "id_key"
    private val jwtTokenKey: String = "token_key"
    private val messageKey: String = "message_key"
    init {
        val id = prefs.getLong(idKey, 0L)
        val jwtToken = prefs.getString(jwtTokenKey, null)
        val message = prefs.getString(messageKey, null)

        if (jwtToken == null) {
            prefs.edit { clear() }
        } else {
            _authStateFlow.value = UserAuthState(id, jwtToken, message ?: "")
        }
    }
    val authStateFlow: StateFlow<UserAuthState?> = _authStateFlow.asStateFlow()
    @Synchronized
    fun setAuth(id: Long, jwtToken: String, message: String? = null) {
        _authStateFlow.value = UserAuthState(id, jwtToken, message ?: "")
        with(prefs.edit()) {
            putLong(idKey, id)
            putString(jwtTokenKey, jwtToken)
            putString(messageKey, message)
            apply()
        }
    }

    @Synchronized
    fun removeAuth() {
        _authStateFlow.value = null
        prefs.edit { clear() }
    }

}