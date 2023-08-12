package kg.ticode.shine.domain.use_case.auth_validate

import android.util.Patterns
import androidx.core.text.isDigitsOnly
import kg.ticode.shine.domain.use_case.ValidationResult

class ValidationEmail {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()){
            return ValidationResult(false, "Адрес электронной почты не может быть пустым")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(false, "Это неверный адрес электронной почты")
        }
        if (email.isDigitsOnly()){
            return ValidationResult(false, "Адрес электронной почты должен содержать по крайней мере одну букву")
        }
        return ValidationResult(true)
    }
}