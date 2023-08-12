package kg.ticode.shine.domain.use_case.auth_validate

import kg.ticode.shine.domain.use_case.ValidationResult

class ValidationAge {
    fun execute(age: Int): ValidationResult {
        if (age.toString().isBlank()) {
            return ValidationResult(false, "Поле возраст не может быть пустым")
        }
        if (age > 150){
            return ValidationResult(false, "Возраст должен быть менее 150 лет")
        }
        if (age <= 18){
            return ValidationResult(false, "Возраст должен быть более 18 лет")
        }
        return ValidationResult(true)
    }
}