package com.geosaude.app.domain.model

data class CadastroFormState(
    val matricula: String = "",
    val funcao: String = "",
    val nomeCompleto: String = "",
    val email: String = "",
    val senha: String = "",
    val confirmarSenha: String = "",

    val matriculaError: String? = null,
    val funcaoError: String? = null,
    val nomeCompletoError: String? = null,
    val emailError: String? = null,
    val senhaError: String? = null,
    val confirmarSenhaError: String? = null,

    val isLoading: Boolean = false
)