package com.geosaude.app.domain.model

/**
 * Estado completo do formulario de Nova Visita.
 * Todos os dados ficam aqui para nao se perderem ao fechar/abrir secoes.
 * Preparado para futura serializacao e envio offline para API.
 */
data class NovaVisitaFormState(
    // --- Secao 1: Dados do imovel ---
    val dataVisita: String = "",
    val codigoLocalidade: String = "",
    val nomeLogradouro: String = "",
    val numero: String = "",
    val ladoQuarteirao: String = "",
    val complemento: String = "",
    val tipoImovel: String = "",
    val nomeMorador: String = "",
    val telefone: String = "",
    val horarioEntrada: String = "",
    val tipoVisita: String = "",
    val inspecionado: String = "",
    val motivoPendencia: String = "",

    // --- Secao 2: Inspecao de depositos ---
    val depositosComFoco: String = "",
    val tipoDeposito: String = "",
    val larvicida: String = "",
    val depositosTratados: String = "",
    val depositosEliminados: String = "",

    // --- Secao 3: Tratamento realizado ---
    val tratamentoAplicado: String = "",
    val tipoLarvicida: String = "",
    val quantidadeLarvicida: String = "",
    val depositosPerifocais: String = "",
    val cargasAdulticida: String = "",

    // --- Secao 4: Amostras e pendencias ---
    val coletaRealizada: String = "",
    val numeroAmostra: String = "",
    val quantidadeTubitos: String = "",

    // --- Secao 5: Observacoes ---
    val observacoes: String = "",

    // --- GPS ---
    val latitude: String = "",
    val longitude: String = "",

    // --- Erros de validacao por campo ---
    val errors: Map<String, String> = emptyMap(),

    // --- Estado geral ---
    val isLoading: Boolean = false,
    val isSaved: Boolean = false
) {
    enum class SectionStatus { EMPTY, PARTIAL, COMPLETE, ERROR }

    // ---------- Helper generico ----------

    /**
     * Calcula o status de uma secao dado:
     * @param todosCampos lista de nomes de todos os campos da secao
     * @param obrigatorios lista de nomes dos campos obrigatorios (pode ser vazia)
     */
    private fun calcularStatus(
        todosCampos: List<String>,
        obrigatorios: List<String> = emptyList()
    ): SectionStatus {
        val valores = toFieldMap()
        val preenchidos = todosCampos.count { (valores[it] ?: "").isNotBlank() }
        val total = todosCampos.size

        // Nenhum campo tocado
        if (preenchidos == 0) return SectionStatus.EMPTY

        // Tem erro de validacao em algum campo da secao
        val temErro = todosCampos.any { errors.containsKey(it) }
        if (temErro) return SectionStatus.ERROR

        // Se tem obrigatorios, verificar se todos estao preenchidos
        if (obrigatorios.isNotEmpty()) {
            val obrigatoriosOk = obrigatorios.all { (valores[it] ?: "").isNotBlank() }
            if (!obrigatoriosOk) return SectionStatus.PARTIAL
        }

        // Todos preenchidos = verde, senao amarelo
        return if (preenchidos == total) SectionStatus.COMPLETE else SectionStatus.PARTIAL
    }

    // ---------- Mapa de campos ----------

    private fun toFieldMap(): Map<String, String> = mapOf(
        "dataVisita" to dataVisita, "codigoLocalidade" to codigoLocalidade,
        "nomeLogradouro" to nomeLogradouro, "numero" to numero,
        "ladoQuarteirao" to ladoQuarteirao, "complemento" to complemento,
        "tipoImovel" to tipoImovel, "nomeMorador" to nomeMorador,
        "telefone" to telefone, "horarioEntrada" to horarioEntrada,
        "tipoVisita" to tipoVisita, "inspecionado" to inspecionado,
        "motivoPendencia" to motivoPendencia,
        "depositosComFoco" to depositosComFoco, "tipoDeposito" to tipoDeposito,
        "larvicida" to larvicida, "depositosTratados" to depositosTratados,
        "depositosEliminados" to depositosEliminados,
        "tratamentoAplicado" to tratamentoAplicado, "tipoLarvicida" to tipoLarvicida,
        "quantidadeLarvicida" to quantidadeLarvicida, "depositosPerifocais" to depositosPerifocais,
        "cargasAdulticida" to cargasAdulticida,
        "coletaRealizada" to coletaRealizada, "numeroAmostra" to numeroAmostra,
        "quantidadeTubitos" to quantidadeTubitos,
        "observacoes" to observacoes
    )

    // ---------- Status por secao ----------

    fun statusSecao1(): SectionStatus = calcularStatus(
        todosCampos = listOf(
            "dataVisita", "codigoLocalidade", "nomeLogradouro", "numero",
            "ladoQuarteirao", "complemento", "tipoImovel", "nomeMorador",
            "telefone", "horarioEntrada", "tipoVisita", "inspecionado", "motivoPendencia"
        ),
        obrigatorios = listOf("dataVisita", "codigoLocalidade", "nomeLogradouro", "numero", "tipoImovel", "inspecionado")
    )

    fun statusSecao2(): SectionStatus = calcularStatus(
        todosCampos = listOf("depositosComFoco", "tipoDeposito", "larvicida", "depositosTratados", "depositosEliminados"),
        obrigatorios = listOf("depositosComFoco")
    )

    fun statusSecao3(): SectionStatus = calcularStatus(
        todosCampos = listOf("tratamentoAplicado", "tipoLarvicida", "quantidadeLarvicida", "depositosPerifocais", "cargasAdulticida")
    )

    fun statusSecao4(): SectionStatus = calcularStatus(
        todosCampos = listOf("coletaRealizada", "numeroAmostra", "quantidadeTubitos")
    )

    fun statusSecao5(): SectionStatus = calcularStatus(
        todosCampos = listOf("observacoes")
    )
}