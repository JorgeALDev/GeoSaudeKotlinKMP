package com.geosaude.app.presentation.screens.recuperacao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosaude.app.domain.model.RecuperacaoSenhaFormState
import com.geosaude.app.domain.validation.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecuperacaoSenhaViewModel : ViewModel() {

    private val _formState = MutableStateFlow(RecuperacaoSenhaFormState())
    val formState: StateFlow<RecuperacaoSenhaFormState> = _formState.asStateFlow()

    fun onMatriculaOuEmailChange(value: String) {
        _formState.value = _formState.value.copy(
            matriculaOuEmail = value.trim(),
            matriculaOuEmailError = null
        )
    }

    fun onEnviar(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _formState.value = _formState.value.copy(isLoading = true)

            val input = _formState.value.matriculaOuEmail

            // Validação: campo não pode estar vazio
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            val validation = when {
                input.isBlank() ->
                    ValidationResult.Error("Informe sua matrícula ou e-mail")
                // Se não for numérico (matrícula), valida como e-mail
                !input.all { it.isDigit() } && !emailRegex.matches(input) ->
                    ValidationResult.Error("Informe um e-mail válido ou sua matrícula")
                else ->
                    ValidationResult.Success
            }

            if (validation is ValidationResult.Error) {
                _formState.value = _formState.value.copy(
                    matriculaOuEmailError = validation.message,
                    isLoading = false
                )
                return@launch
            }

            // TODO: Integrar com API real de recuperação de senha
            kotlinx.coroutines.delay(1500)

            _formState.value = _formState.value.copy(isLoading = false)
            onSuccess()
        }
    }
}