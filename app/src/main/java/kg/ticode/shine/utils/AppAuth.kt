package kg.ticode.shine.utils

import android.content.Context
import android.content.SharedPreferences
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

    private val prefs: SharedPreferences =
        context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
    private val _authStateFlow = MutableStateFlow<UserAuthState?>(null)
    private val idKey: String = "id_key"
    private val jwtTokenKey: String = "token_key"
    private val messageKey: String = "message_key"
    private val roleKey: String = "role_key"

    init {
        val id = prefs.getLong(idKey, 0L)
        val jwtToken = prefs.getString(jwtTokenKey, null)
        val message = prefs.getString(messageKey, null)
        val role = prefs.getString(roleKey, null)

        if (jwtToken == null) {
            prefs.edit { clear() }
        } else {
            _authStateFlow.value = UserAuthState(id, jwtToken, message ?: "", role ?: "")
        }
    }

    val authStateFlow: StateFlow<UserAuthState?> = _authStateFlow.asStateFlow()

    @Synchronized
    fun setAuth(id: Long, jwtToken: String, message: String? = null, role: String) {
        _authStateFlow.value = UserAuthState(id, jwtToken, message ?: "", role)
        with(prefs.edit()) {
            putLong(idKey, id)
            putString(jwtTokenKey, jwtToken)
            putString(messageKey, message)
            putString(roleKey, role)
            apply()
        }
    }

    @Synchronized
    fun changeToken(token: String) {
        _authStateFlow.value = authStateFlow.value?.copy(jwtToken = token)
        with(prefs.edit()) {
            putString(jwtTokenKey, token)
            apply()
        }
    }

    @Synchronized
    fun removeAuth() {
        _authStateFlow.value = null
        prefs.edit { clear() }
    }

}