package kg.ticode.shine.dto

import kg.ticode.shine.model.RegistrationUserRequest

data class RegistrationDataToVerify(val verifyID: String, val registrationUserRequest: RegistrationUserRequest)