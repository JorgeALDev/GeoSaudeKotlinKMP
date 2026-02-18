package com.geosaude.app.ui.screens.cadastro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosaude.app.domain.model.CadastroFormState
import com.geosaude.app.domain.validation.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CadastroViewModel : ViewModel() {

    private val _formState = MutableStateFlow(CadastroFormState())
    val formState: StateFlow<CadastroFormState> = _formState.asStateFlow()

    fun onMatriculaChange(value: String) {
        val filtered = value.filter { it.isDigit() }.take(8)
        _formState.value = _formState.value.copy(
            matricula = filtered,
            matriculaError = null
        )
    }

    fun onFuncaoChange(value: String) {
        _formState.value = _formState.value.copy(
            funcao = value,
            funcaoError = null
        )
    }

    fun onNomeCompletoChange(value: String) {
        _formState.value = _formState.value.copy(
            nomeCompleto = value,
            nomeCompletoError = null
        )
    }

    fun onEmailChange(value: String) {
        _formState.value = _formState.value.copy(
            email = value.trim(),
            emailError = null
        )
    }

    fun onSenhaChange(value: String) {
        _formState.value = _formState.value.copy(
            senha = value,
            senhaError = null
        )
    }

    fun onConfirmarSenhaChange(value: String) {
        _formState.value = _formState.value.copy(
            confirmarSenha = value,
            confirmarSenhaError = null
        )
    }

    fun onCadastrar(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _formState.value = _formState.value.copy(isLoading = true)

            // Validar todos os campos
            val matriculaValidation = MatriculaValidator.validate(_formState.value.matricula)
            val funcaoValidation = FuncaoValidator.validate(_formState.value.funcao)
            val nomeValidation = NomeValidator.validate(_formState.value.nomeCompleto)
            val emailValidation = EmailValidator.validate(_formState.value.email)
            val senhaValidation = SenhaValidator.validate(_formState.value.senha)
            val confirmacaoValidation = SenhaValidator.validateConfirmation(
                _formState.value.senha,
                _formState.value.confirmarSenha
            )

            // Coletar erros
            val hasErrors = listOf(
                matriculaValidation,
                funcaoValidation,
                nomeValidation,
                emailValidation,
                senhaValidation,
                confirmacaoValidation
            ).any { it !is ValidationResult.Success }

            if (hasErrors) {
                _formState.value = _formState.value.copy(
                    matriculaError = (matriculaValidation as? ValidationResult.Error)?.message,
                    funcaoError = (funcaoValidation as? ValidationResult.Error)?.message,
                    nomeCompletoError = (nomeValidation as? ValidationResult.Error)?.message,
                    emailError = (emailValidation as? ValidationResult.Error)?.message,
                    senhaError = (senhaValidation as? ValidationResult.Error)?.message,
                    confirmarSenhaError = (confirmacaoValidation as? ValidationResult.Error)?.message,
                    isLoading = false
                )
                return@launch
            }

            // TODO: Integrar com API real
            kotlinx.coroutines.delay(1500)

            // Cadastro bem-sucedido
            _formState.value = _formState.value.copy(isLoading = false)
            onSuccess()
        }
    }
}