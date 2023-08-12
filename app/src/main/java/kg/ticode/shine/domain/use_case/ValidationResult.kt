package kg.ticode.shine.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val reason: String? = null
)