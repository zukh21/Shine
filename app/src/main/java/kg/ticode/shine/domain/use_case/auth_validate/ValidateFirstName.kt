package kg.ticode.shine.domain.use_case.auth_validate

import kg.ticode.shine.domain.use_case.ValidationResult

class ValidateFirstName {
    fun execute(firstName: String): ValidationResult {
        if (firstName.isBlank()){
            return ValidationResult(false, "Поле Имя не может быть пустым")
        }
        return ValidationResult(true)
    }
}