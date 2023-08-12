package kg.ticode.shine.domain.use_case.auth_validate

import kg.ticode.shine.domain.use_case.ValidationResult

class ValidationLastName {
    fun execute(lastName: String): ValidationResult {
        if (lastName.isBlank()) {
            return ValidationResult(false, "Поле фамилия не может быть пустым")
        }
        return ValidationResult(true)
    }

}