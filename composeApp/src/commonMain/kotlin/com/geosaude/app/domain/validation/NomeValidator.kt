package com.geosaude.app.domain.validation

object NomeValidator {
    private const val MIN_LENGTH = 3

    fun validate(nome: String): ValidationResult {
        return when {
            nome.isBlank() ->
                ValidationResult.Error("Nome é obrigatório")

            nome.trim().length < MIN_LENGTH ->
                ValidationResult.Error("Nome deve ter no mínimo $MIN_LENGTH caracteres")

            !nome.contains(" ") ->
                ValidationResult.Error("Digite o nome completo")

            else -> ValidationResult.Success
        }
    }
}