package com.geosaude.app.domain.validation

object EmailValidator {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    fun validate(email: String): ValidationResult {
        return when {
            email.isBlank() ->
                ValidationResult.Error("E-mail é obrigatório")

            !EMAIL_REGEX.matches(email) ->
                ValidationResult.Error("E-mail inválido")

            else -> ValidationResult.Success
        }
    }
}