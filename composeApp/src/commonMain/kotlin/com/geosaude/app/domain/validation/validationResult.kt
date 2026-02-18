package com.geosaude.app.domain.validation

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

fun ValidationResult.isSuccess(): Boolean = this is ValidationResult.Success
fun ValidationResult.errorMessage(): String? = (this as? ValidationResult.Error)?.message