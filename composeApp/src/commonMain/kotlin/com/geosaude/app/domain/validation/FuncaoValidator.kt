package com.geosaude.app.domain.validation

object FuncaoValidator {
    private val FUNCOES_VALIDAS = listOf("Agente de Campo", "Supervisor")

    fun validate(funcao: String): ValidationResult {
        return when {
            funcao.isBlank() ->
                ValidationResult.Error("Função é obrigatória")

            funcao !in FUNCOES_VALIDAS ->
                ValidationResult.Error("Função inválida")

            else -> ValidationResult.Success
        }
    }

    fun funcoesDisponiveis(): List<String> = FUNCOES_VALIDAS
}