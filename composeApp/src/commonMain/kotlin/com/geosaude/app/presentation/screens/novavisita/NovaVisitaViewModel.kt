package com.geosaude.app.presentation.screens.novavisita

import androidx.lifecycle.ViewModel
import com.geosaude.app.domain.model.NovaVisitaFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NovaVisitaViewModel : ViewModel() {

    private val _formState = MutableStateFlow(NovaVisitaFormState())
    val formState: StateFlow<NovaVisitaFormState> = _formState.asStateFlow()

    // -----------------------------------------------------------------------
    // Mascaras de formatacao
    // -----------------------------------------------------------------------

    /** Formata data automaticamente: 12032025 → 12/03/2025 */
    private fun formatDate(raw: String): String {
        val digits = raw.filter { it.isDigit() }.take(8)
        return buildString {
            digits.forEachIndexed { i, c ->
                if (i == 2 || i == 4) append('/')
                append(c)
            }
        }
    }

    /**
     * Formata telefone no padrao brasileiro:
     * Fixo   (10 dig): (11) 3456-7890  → (XX) XXXX-XXXX
     * Celular(11 dig): (11) 99456-7890 → (XX) XXXXX-XXXX
     *
     * O traco sempre fica 4 digitos antes do final.
     */
    private fun formatPhone(raw: String): String {
        val digits = raw.filter { it.isDigit() }.take(11)
        val len = digits.length

        return buildString {
            for (i in digits.indices) {
                when {
                    i == 0 -> append("(${digits[i]}")
                    i == 1 -> append("${digits[i]}) ")
                    // Posicao do traco depende se e fixo (8 dig corpo) ou celular (9 dig corpo)
                    // Corpo = tudo apos o DDD (2 digitos)
                    // Traco fica 4 antes do fim: posicao = len - 4
                    i == len - 4 && i > 2 -> append("-${digits[i]}")
                    else -> append(digits[i])
                }
            }
        }
    }

    /** Formata horario: 1430 → 14:30 */
    private fun formatTime(raw: String): String {
        val digits = raw.filter { it.isDigit() }.take(4)
        return buildString {
            digits.forEachIndexed { i, c ->
                if (i == 2) append(':')
                append(c)
            }
        }
    }

    /** Permite apenas digitos, sem negativos. */
    private fun onlyDigits(raw: String, max: Int = 10): String {
        return raw.filter { it.isDigit() }.take(max)
    }

    /** Permite digitos e ponto decimal. */
    private fun onlyDecimal(raw: String): String {
        var hasDot = false
        return raw.filter {
            when {
                it.isDigit() -> true
                it == '.' && !hasDot -> { hasDot = true; true }
                else -> false
            }
        }.take(10)
    }

    // -----------------------------------------------------------------------
    // Secao 1: Dados do imovel
    // -----------------------------------------------------------------------

    fun onDataVisitaChange(value: String) {
        val formatted = formatDate(value)
        updateState { copy(dataVisita = formatted, errors = errors - "dataVisita") }
    }

    fun onCodigoLocalidadeChange(value: String) {
        updateState { copy(codigoLocalidade = value.take(50), errors = errors - "codigoLocalidade") }
    }

    fun onNomeLogradouroChange(value: String) {
        updateState { copy(nomeLogradouro = value.take(100), errors = errors - "nomeLogradouro") }
    }

    fun onNumeroChange(value: String) {
        updateState { copy(numero = onlyDigits(value, 6), errors = errors - "numero") }
    }

    fun onLadoQuarteiraoChange(value: String) {
        updateState { copy(ladoQuarteirao = value.take(30)) }
    }

    fun onComplementoChange(value: String) {
        updateState { copy(complemento = value.take(50)) }
    }

    fun onTipoImovelChange(value: String) {
        updateState { copy(tipoImovel = value, errors = errors - "tipoImovel") }
    }

    fun onNomeMoradorChange(value: String) {
        updateState { copy(nomeMorador = value.take(100)) }
    }

    fun onTelefoneChange(value: String) {
        val formatted = formatPhone(value)
        updateState { copy(telefone = formatted) }
    }

    fun onHorarioEntradaChange(value: String) {
        val formatted = formatTime(value)
        updateState { copy(horarioEntrada = formatted) }
    }

    fun onTipoVisitaChange(value: String) {
        updateState { copy(tipoVisita = value) }
    }

    fun onInspecionadoChange(value: String) {
        updateState { copy(inspecionado = value, errors = errors - "inspecionado") }
    }

    fun onMotivoPendenciaChange(value: String) {
        updateState { copy(motivoPendencia = value) }
    }

    // -----------------------------------------------------------------------
    // Secao 2: Inspecao de depositos
    // -----------------------------------------------------------------------

    fun onDepositosComFocoChange(value: String) {
        updateState { copy(depositosComFoco = value, errors = errors - "depositosComFoco") }
    }

    fun onTipoDepositoChange(value: String) {
        updateState { copy(tipoDeposito = value) }
    }

    fun onLarvicidaChange(value: String) {
        updateState { copy(larvicida = value) }
    }

    fun onDepositosTratadosChange(value: String) {
        updateState { copy(depositosTratados = onlyDigits(value, 5)) }
    }

    fun onDepositosEliminadosChange(value: String) {
        updateState { copy(depositosEliminados = onlyDigits(value, 5)) }
    }

    // -----------------------------------------------------------------------
    // Secao 3: Tratamento realizado
    // -----------------------------------------------------------------------

    fun onTratamentoAplicadoChange(value: String) {
        updateState { copy(tratamentoAplicado = value) }
    }

    fun onTipoLarvicidaChange(value: String) {
        updateState { copy(tipoLarvicida = value) }
    }

    fun onQuantidadeLarvicidaChange(value: String) {
        updateState { copy(quantidadeLarvicida = onlyDecimal(value)) }
    }

    fun onDepositosPerifocaisChange(value: String) {
        updateState { copy(depositosPerifocais = onlyDigits(value, 5)) }
    }

    fun onCargasAdulticidaChange(value: String) {
        updateState { copy(cargasAdulticida = onlyDigits(value, 5)) }
    }

    // -----------------------------------------------------------------------
    // Secao 4: Amostras e pendencias
    // -----------------------------------------------------------------------

    fun onColetaRealizadaChange(value: String) {
        updateState { copy(coletaRealizada = value) }
    }

    fun onNumeroAmostraChange(value: String) {
        updateState { copy(numeroAmostra = value.take(30)) }
    }

    fun onQuantidadeTubitosChange(value: String) {
        updateState { copy(quantidadeTubitos = onlyDigits(value, 5)) }
    }

    // -----------------------------------------------------------------------
    // Secao 5: Observacoes
    // -----------------------------------------------------------------------

    fun onObservacoesChange(value: String) {
        updateState { copy(observacoes = value.take(500)) }
    }

    // -----------------------------------------------------------------------
    // GPS
    // -----------------------------------------------------------------------

    fun onGpsCaptured(lat: String, lng: String) {
        updateState { copy(latitude = lat, longitude = lng) }
    }

    // -----------------------------------------------------------------------
    // Validacao completa antes de enviar
    // -----------------------------------------------------------------------

    fun validateAll(): Boolean {
        val newErrors = mutableMapOf<String, String>()
        val state = _formState.value

        // Secao 1 - obrigatorios
        if (state.dataVisita.isBlank()) newErrors["dataVisita"] = "Data e obrigatoria"
        else if (!isValidDate(state.dataVisita)) newErrors["dataVisita"] = "Data invalida (DD/MM/AAAA)"

        if (state.codigoLocalidade.isBlank()) newErrors["codigoLocalidade"] = "Localidade e obrigatoria"
        if (state.nomeLogradouro.isBlank()) newErrors["nomeLogradouro"] = "Logradouro e obrigatorio"
        if (state.numero.isBlank()) newErrors["numero"] = "Numero e obrigatorio"
        if (state.tipoImovel.isBlank()) newErrors["tipoImovel"] = "Tipo de imovel e obrigatorio"
        if (state.inspecionado.isBlank()) newErrors["inspecionado"] = "Informe se foi inspecionado"

        // Validar telefone se preenchido (minimo DDD + 8 digitos)
        if (state.telefone.isNotBlank()) {
            val phoneDigits = state.telefone.filter { it.isDigit() }
            if (phoneDigits.length < 10) newErrors["telefone"] = "Telefone incompleto"
        }

        // Validar horario se preenchido
        if (state.horarioEntrada.isNotBlank() && !isValidTime(state.horarioEntrada)) {
            newErrors["horarioEntrada"] = "Horario invalido (HH:MM)"
        }

        // Secao 2 - obrigatorios
        if (state.depositosComFoco.isBlank()) newErrors["depositosComFoco"] = "Campo obrigatorio"

        _formState.value = state.copy(errors = newErrors)
        return newErrors.isEmpty()
    }

    fun onSubmit(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!validateAll()) {
            onError("Preencha todos os campos obrigatorios")
            return
        }

        updateState { copy(isLoading = true) }

        // TODO: Tentar enviar para API. Se offline, salvar localmente.
        updateState { copy(isLoading = false, isSaved = true) }
        onSuccess()
    }

    fun resetForm() {
        _formState.value = NovaVisitaFormState()
    }

    // -----------------------------------------------------------------------
    // Helpers de validacao
    // -----------------------------------------------------------------------

    private fun isValidDate(date: String): Boolean {
        if (date.length != 10) return false
        val parts = date.split("/")
        if (parts.size != 3) return false
        val day = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val year = parts[2].toIntOrNull() ?: return false
        return day in 1..31 && month in 1..12 && year in 2020..2099
    }

    private fun isValidTime(time: String): Boolean {
        if (time.length != 5) return false
        val parts = time.split(":")
        if (parts.size != 2) return false
        val hour = parts[0].toIntOrNull() ?: return false
        val minute = parts[1].toIntOrNull() ?: return false
        return hour in 0..23 && minute in 0..59
    }

    private fun updateState(block: NovaVisitaFormState.() -> NovaVisitaFormState) {
        _formState.value = _formState.value.block()
    }
}