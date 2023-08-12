package kg.ticode.shine.domain.use_case.auth_validate

import android.util.Patterns
import kg.ticode.shine.domain.use_case.ValidationResult

class ValidatePhoneNumber {
    fun execute(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()){
            return ValidationResult(false, "Поле номер телефона не может быть пустым")
        }
        if (!Patterns.PHONE.matcher(phoneNumber).matches()){
            return ValidationResult(false, "Это недействительный номер телефона")
        }
        if (phoneNumber.length > 12){
            return ValidationResult(false, "Номер телефона должен быть меньше или равен 12")
        }
        return ValidationResult(true)
    }
}