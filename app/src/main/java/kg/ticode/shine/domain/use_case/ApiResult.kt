package kg.ticode.shine.domain.use_case

sealed class ApiResult {
    object Loading: ApiResult()
    object Success : ApiResult()
    object Timeout: ApiResult()
    object Error: ApiResult()
}
