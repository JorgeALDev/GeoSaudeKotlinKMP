package com.geosaude.app.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosaude.app.domain.model.LoginFormState
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

    fun onLogin(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            // Validações
            if (!validarFormulario()) {
                return@launch
            }

            _formState.value = _formState.value.copy(isLoading = true)

            // TODO: Implementar lógica de login real
            // Por enquanto, mock simples
            kotlinx.coroutines.delay(1000) // Simular chamada de API

            // Mock: Supervisor
            if (_formState.value.matricula == "123456" && _formState.value.senha == "Admin123") {
                _formState.value = _formState.value.copy(isLoading = false)
                onSuccess("supervisor")
                return@launch
            }

            // Mock: Agente
            if (_formState.value.matricula == "789012" && _formState.value.senha == "Agente123") {
                _formState.value = _formState.value.copy(isLoading = false)
                onSuccess("agent")
                return@launch
            }

            // Erro
            _formState.value = _formState.value.copy(isLoading = false)
            onError("Matrícula ou senha incorretos")
        }
    }

    private fun validarFormulario(): Boolean {
        var isValid = true

        // Validar matrícula (6-8 dígitos)
        if (_formState.value.matricula.length !in 6..8) {
            _formState.value = _formState.value.copy(
                matriculaError = "Matrícula deve ter entre 6 e 8 dígitos"
            )
            isValid = false
        }

        // Validar senha (não vazia)
        if (_formState.value.senha.isBlank()) {
            _formState.value = _formState.value.copy(
                senhaError = "Senha é obrigatória"
            )
            isValid = false
        }

        return isValid
    }
}