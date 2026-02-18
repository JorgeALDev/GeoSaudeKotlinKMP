package com.geosaude.app.domain.validation

object MatriculaValidator {
    private const val MATRICULA_LENGTH = 8

    fun validate(matricula: String): ValidationResult {
        return when {
            matricula.isBlank() ->
                ValidationResult.Error("Matrícula é obrigatória")

            matricula.length < MATRICULA_LENGTH ->
                ValidationResult.Error("Matrícula deve ter $MATRICULA_LENGTH dígitos")

            !matricula.all { it.isDigit() } ->
                ValidationResult.Error("Matrícula deve conter apenas números")

            else -> ValidationResult.Success
        }
    }
}