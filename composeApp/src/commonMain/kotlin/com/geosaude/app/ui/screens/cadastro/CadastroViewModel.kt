package com.geosaude.app.ui.screens.cadastro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosaude.app.domain.model.CadastroFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CadastroViewModel : ViewModel() {

    private val _formState = MutableStateFlow(CadastroFormState())
    val formState: StateFlow<CadastroFormState> = _formState.asStateFlow()

    fun onMatriculaChange(value: String) {
        // Permitir apenas números, máximo 8 dígitos
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
            email = value,
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

    fun onCadastrar(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            // Validações
            if (!validarFormulario()) {
                return@launch
            }

            _formState.value = _formState.value.copy(isLoading = true)

            // TODO: Implementar cadastro real
            kotlinx.coroutines.delay(1500) // Simular chamada de API

            _formState.value = _formState.value.copy(isLoading = false)
            onSuccess()
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

        // Validar função
        if (_formState.value.funcao.isBlank()) {
            _formState.value = _formState.value.copy(
                funcaoError = "Selecione uma função"
            )
            isValid = false
        }

        // Validar nome completo (mínimo 3 caracteres)
        if (_formState.value.nomeCompleto.length < 3) {
            _formState.value = _formState.value.copy(
                nomeCompletoError = "Nome deve ter no mínimo 3 caracteres"
            )
            isValid = false
        }

        // Validar email (regex simples)
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (!_formState.value.email.matches(emailRegex)) {
            _formState.value = _formState.value.copy(
                emailError = "E-mail inválido"
            )
            isValid = false
        }

        // Validar senha (mínimo 6 caracteres)
        if (_formState.value.senha.length < 6) {
            _formState.value = _formState.value.copy(
                senhaError = "Senha deve ter no mínimo 6 caracteres"
            )
            isValid = false
        }

        // Validar confirmação de senha
        if (_formState.value.senha != _formState.value.confirmarSenha) {
            _formState.value = _formState.value.copy(
                confirmarSenhaError = "As senhas não coincidem"
            )
            isValid = false
        }

        return isValid
    }
}