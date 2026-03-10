package com.geosaude.app.domain.model

/**
 * Estado do formulário de recuperação de senha.
 *
 * @param matriculaOuEmail Valor digitado no campo de matrícula ou e-mail.
 * @param matriculaOuEmailError Mensagem de erro do campo, ou null se válido.
 * @param isLoading Indica se o envio está em progresso.
 */
data class RecuperacaoSenhaFormState(
    val matriculaOuEmail: String = "",
    val matriculaOuEmailError: String? = null,
    val isLoading: Boolean = false
)
