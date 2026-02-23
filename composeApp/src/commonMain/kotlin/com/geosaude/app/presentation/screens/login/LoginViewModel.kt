package com.geosaude.app.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosaude.app.domain.model.LoginFormState
import com.geosaude.app.domain.validation.MatriculaValidator
import com.geosaude.app.domain.validation.SenhaValidator
import com.geosaude.app.domain.validation.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _formState = MutableStateFlow(LoginFormState())
    val formState: StateFlow<LoginFormState> = _formState.asStateFlow()

    fun onMatriculaChange(value: String) {
        // Permitir apenas números
        val filtered = value.filter { it.isDigit() }.take(8)
        _formState.value = _formState.value.copy(
            matricula = filtered,
            matriculaError = null
        )
    }

    fun onSenhaChange(value: String) {
        _formState.value = _formState.value.copy(
            senha = value,
            senhaError = null
        )
    }

    fun onLogin(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _formState.value = _formState.value.copy(isLoading = true)

            // Validar matrícula
            val matriculaValidation = MatriculaValidator.validate(_formState.value.matricula)
            if (matriculaValidation !is ValidationResult.Success) {
                _formState.value = _formState.value.copy(
                    matriculaError = (matriculaValidation as ValidationResult.Error).message,
                    isLoading = false
                )
                return@launch
            }

            // Validar senha
            val senhaValidation = SenhaValidator.validate(_formState.value.senha)
            if (senhaValidation !is ValidationResult.Success) {
                _formState.value = _formState.value.copy(
                    senhaError = (senhaValidation as ValidationResult.Error).message,
                    isLoading = false
                )
                return@launch
            }

            // TODO: Integrar com API real
            // A API deve retornar a função do usuário baseado na matrícula

            // Simulação de login
            kotlinx.coroutines.delay(1000)

            // Login bem-sucedido
            _formState.value = _formState.value.copy(isLoading = false)
            onSuccess("agente_de_campo")
        }
    }
}