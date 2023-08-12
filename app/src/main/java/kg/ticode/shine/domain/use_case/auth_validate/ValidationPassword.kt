package kg.ticode.shine.domain.use_case.auth_validate

import kg.ticode.shine.domain.use_case.ValidationResult

class ValidationPassword {
    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(false, "Пароль не может быть пустым")
        }
        if (password.length < 8) {
            return ValidationResult(false, "Пароль должен состоять как минимум из 8 символов")
        }
        val containsLetterAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLetterAndDigits){
            return ValidationResult(false, "Пароль должен содержать по крайней мере одну букву и цифру")
        }
        return ValidationResult(true)
    }
}