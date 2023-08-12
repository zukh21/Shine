package kg.ticode.shine.model

data class RegistrationFormState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val age: Int = 0,
    val ageError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)