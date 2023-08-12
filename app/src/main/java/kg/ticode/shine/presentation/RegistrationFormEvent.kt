package kg.ticode.shine.presentation

sealed class RegistrationFormEvent {
    data class FirstNameChanged(val firstName: String): RegistrationFormEvent()
    data class LastNameChanged(val lastName: String): RegistrationFormEvent()
    data class PhoneNumberChanged(val phoneNumber: String): RegistrationFormEvent()
    data class EmailChanged(val email: String): RegistrationFormEvent()
    data class AgeChanged(val age: Int): RegistrationFormEvent()
    data class PasswordChanged(val password: String): RegistrationFormEvent()
    object Registration: RegistrationFormEvent()
    object AuthorizationWithPhone: RegistrationFormEvent()
    object AuthorizationWithEmail: RegistrationFormEvent()
}