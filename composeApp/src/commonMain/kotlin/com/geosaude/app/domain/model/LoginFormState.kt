package com.geosaude.app.domain.model

data class LoginFormState(
    val matricula: String = "",
    val senha: String = "",
    val matriculaError: String? = null,
    val senhaError: String? = null,
    val isLoading: Boolean = false
)