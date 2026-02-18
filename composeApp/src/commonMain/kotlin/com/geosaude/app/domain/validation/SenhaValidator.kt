package com.geosaude.app.domain.validation

object SenhaValidator {
    private const val MIN_LENGTH = 6

    fun validate(senha: String): ValidationResult {
        return when {
            senha.isBlank() ->
                ValidationResult.Error("Senha é obrigatória")

            senha.length < MIN_LENGTH ->
                ValidationResult.Error("Senha deve ter no mínimo $MIN_LENGTH caracteres")

            else -> ValidationResult.Success
        }
    }

    fun validateConfirmation(senha: String, confirmacao: String): ValidationResult {
        return when {
            confirmacao.isBlank() ->
                ValidationResult.Error("Confirmação de senha é obrigatória")

            senha != confirmacao ->
                ValidationResult.Error("As senhas não coincidem")

            else -> ValidationResult.Success
        }
    }
}