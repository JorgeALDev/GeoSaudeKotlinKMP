package com.geosaude.app.presentation.screens.novavisita

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.domain.model.NovaVisitaFormState
import com.geosaude.app.domain.model.NovaVisitaFormState.SectionStatus
import com.geosaude.app.presentation.components.LogoHeader
import com.geosaude.app.presentation.theme.GeoSaudeColors

// ---------------------------------------------------------------------------
// Cores do formulario
// ---------------------------------------------------------------------------

private val BackgroundGreen = Color(0xFFE8F5E9)
private val FieldGreen = Color(0xFFD4E8D7)
private val CardWhite = Color.White
private val TextGray = Color(0xFF5F6368)
private val LabelGray = Color(0xFF80868B)

// Cores de status das secoes
private val StatusEmpty = Color(0xFFBDBDBD)
private val StatusPartial = Color(0xFFFFA726)
private val StatusComplete = Color(0xFF4CAF50)
private val StatusError = Color(0xFFEF5350)

// ---------------------------------------------------------------------------
// Ponto de entrada
// ---------------------------------------------------------------------------

@Composable
fun NovaVisitaScreen() {
    val viewModel = org.koin.compose.koinInject<NovaVisitaViewModel>()
    val formState by viewModel.formState.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth >= 800.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGreen)
        ) {
            if (isDesktop) DesktopHeader() else MobileHeader()
            SectionsList(
                isDesktop = isDesktop,
                viewModel = viewModel,
                formState = formState
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Headers
// ---------------------------------------------------------------------------

@Composable
private fun MobileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundGreen)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoHeader(backgroundColor = BackgroundGreen, modifier = Modifier.weight(1f))
        GpsCaptureButton()
    }
}

@Composable
private fun DesktopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundGreen)
            .padding(horizontal = 32.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text("Nova visita", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = GeoSaudeColors.TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Preencha os dados da inspecao", fontSize = 14.sp, color = LabelGray)
        }
        GpsCaptureButton()
    }
}

// ---------------------------------------------------------------------------
// Lista de secoes
// ---------------------------------------------------------------------------

@Composable
private fun SectionsList(
    isDesktop: Boolean,
    viewModel: NovaVisitaViewModel,
    formState: NovaVisitaFormState
) {
    val horizontalPadding = if (isDesktop) 32.dp else 16.dp
    var submitError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontalPadding, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExpandableSection(
            title = "Dados do imovel",
            status = formState.statusSecao1().toColor()
        ) {
            InformacoesGeraisContent(viewModel, formState, isDesktop)
        }

        ExpandableSection(
            title = "Inspecao de depositos e tratamento realizado",
            status = formState.statusSecao2().toColor()
        ) {
            InspecaoDepositosContent(viewModel, formState, isDesktop)
        }

        ExpandableSection(
            title = "Tratamento realizado",
            status = formState.statusSecao3().toColor()
        ) {
            TratamentoRealizadoContent(viewModel, formState, isDesktop)
        }

        ExpandableSection(
            title = "Amostras e pendencias",
            status = formState.statusSecao4().toColor()
        ) {
            AmostrasPendenciasContent(viewModel, formState, isDesktop)
        }

        ExpandableSection(
            title = "Observacoes",
            status = formState.statusSecao5().toColor()
        ) {
            ObservacoesContent(viewModel, formState)
        }

        // Erro geral de validacao
        if (submitError != null) {
            Text(submitError!!, color = StatusError, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botao de envio
        Button(
            onClick = {
                submitError = null
                viewModel.onSubmit(
                    onSuccess = { submitError = null /* TODO: navegar ou mostrar confirmacao */ },
                    onError = { submitError = it }
                )
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = !formState.isLoading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GeoSaudeColors.Primary)
        ) {
            if (formState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.dp)
            } else {
                Text("Finalizar e Enviar Visita", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(if (isDesktop) 24.dp else 16.dp))
    }
}

// ---------------------------------------------------------------------------
// Secao expandivel com status colorido
// ---------------------------------------------------------------------------

@Composable
private fun ExpandableSection(
    title: String,
    status: Color = StatusEmpty,
    content: @Composable ColumnScope.() -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bolinha de status
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(status, RoundedCornerShape(5.dp))
                )

                Text(
                    text = title, fontSize = 15.sp,
                    fontWeight = FontWeight.Medium, color = TextGray,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Recolher" else "Expandir",
                    tint = GeoSaudeColors.Primary
                )
            }

            if (isExpanded) {
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = content
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Conteudo Secao 1: Dados do imovel
// ---------------------------------------------------------------------------

@Composable
private fun InformacoesGeraisContent(
    vm: NovaVisitaViewModel,
    state: NovaVisitaFormState,
    isDesktop: Boolean
) {
    FormField("Data da visita *", state.dataVisita, vm::onDataVisitaChange, "DD/MM/AAAA", KeyboardType.Number, error = state.errors["dataVisita"])
    FormField("Codigo e nome da localidade/bairro *", state.codigoLocalidade, vm::onCodigoLocalidadeChange, "Ex: 001 - Centro", error = state.errors["codigoLocalidade"])
    FormField("Nome do logradouro (Rua/Av) *", state.nomeLogradouro, vm::onNomeLogradouroChange, "Ex: Rua das Acacias", error = state.errors["nomeLogradouro"])

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            FormField("Numero *", state.numero, vm::onNumeroChange, "123", KeyboardType.Number, error = state.errors["numero"])
        }
        Column(modifier = Modifier.weight(1f)) {
            FormField("Lado do quarteirao", state.ladoQuarteirao, vm::onLadoQuarteiraoChange, "Ex: Direito")
        }
    }

    FormField("Complemento", state.complemento, vm::onComplementoChange, "Ex: Apto 201, Casa 2")

    FormDropdown(
        "Tipo de imovel *", state.tipoImovel, vm::onTipoImovelChange,
        listOf("Residencial", "Comercio", "Terreno baldio", "Ponto estrategico", "Escola", "Outro"),
        error = state.errors["tipoImovel"], isDesktop = isDesktop
    )

    FormField("Nome do morador/responsavel", state.nomeMorador, vm::onNomeMoradorChange, "Nome completo")
    FormField("Telefone para contato", state.telefone, vm::onTelefoneChange, "(00) 00000-0000", KeyboardType.Phone)
    FormField("Horario de entrada no imovel", state.horarioEntrada, vm::onHorarioEntradaChange, "HH:MM", KeyboardType.Number, error = state.errors["horarioEntrada"])

    FormDropdown(
        "Tipo de visita", state.tipoVisita, vm::onTipoVisitaChange,
        listOf("Normal", "Recuperacao", "LIRAa", "Bloqueio"),
        hint = "Normal = Rotina | Recuperacao = Reinspecao", isDesktop = isDesktop
    )

    FormDropdown(
        "O imovel foi inspecionado? *", state.inspecionado, vm::onInspecionadoChange,
        listOf("Sim", "Nao"),
        hint = "Se NAO, preencha o motivo da pendencia",
        error = state.errors["inspecionado"], isDesktop = isDesktop
    )

    FormDropdown(
        "Motivo da pendencia", state.motivoPendencia, vm::onMotivoPendenciaChange,
        listOf("Nenhuma", "Morador recusou", "Imovel fechado", "Ausente", "Imovel abandonado", "Outro"),
        isDesktop = isDesktop
    )
}

// ---------------------------------------------------------------------------
// Conteudo Secao 2: Inspecao de depositos
// ---------------------------------------------------------------------------

@Composable
private fun InspecaoDepositosContent(
    vm: NovaVisitaViewModel,
    state: NovaVisitaFormState,
    isDesktop: Boolean
) {
    FormDropdown(
        "Depositos com foco encontrados? *", state.depositosComFoco, vm::onDepositosComFocoChange,
        listOf("Sim", "Nao"), error = state.errors["depositosComFoco"], isDesktop = isDesktop
    )
    FormDropdown(
        "Tipo de deposito", state.tipoDeposito, vm::onTipoDepositoChange,
        listOf("A1", "A2", "B", "C", "D1", "D2", "E"), isDesktop = isDesktop
    )
    FormDropdown(
        "Larvicida utilizado", state.larvicida, vm::onLarvicidaChange,
        listOf("Nenhum", "BTI", "Pyriproxyfen", "Diflubenzuron", "Temefos", "Outro"),
        isDesktop = isDesktop
    )
    FormField("Depositos tratados", state.depositosTratados, vm::onDepositosTratadosChange, keyboardType = KeyboardType.Number)
    FormField("Depositos eliminados", state.depositosEliminados, vm::onDepositosEliminadosChange, keyboardType = KeyboardType.Number)
}

// ---------------------------------------------------------------------------
// Conteudo Secao 3: Tratamento realizado
// ---------------------------------------------------------------------------

@Composable
private fun TratamentoRealizadoContent(
    vm: NovaVisitaViewModel,
    state: NovaVisitaFormState,
    isDesktop: Boolean
) {
    FormDropdown(
        "Foi aplicado algum tratamento?", state.tratamentoAplicado, vm::onTratamentoAplicadoChange,
        listOf("Sim", "Nao"), isDesktop = isDesktop
    )
    FormDropdown(
        "Tipo de larvicida utilizado", state.tipoLarvicida, vm::onTipoLarvicidaChange,
        listOf("Nenhum", "BTI", "Pyriproxyfen", "Diflubenzuron", "Temefos", "Outro"),
        isDesktop = isDesktop
    )
    FormField("Quantidade de larvicida (g ou ml)", state.quantidadeLarvicida, vm::onQuantidadeLarvicidaChange, "Ex: 10.5", KeyboardType.Decimal)
    FormField("Depositos tratados (perifocal)", state.depositosPerifocais, vm::onDepositosPerifocaisChange, keyboardType = KeyboardType.Number)
    FormField("Cargas de adulticida aplicadas", state.cargasAdulticida, vm::onCargasAdulticidaChange, keyboardType = KeyboardType.Number)
}

// ---------------------------------------------------------------------------
// Conteudo Secao 4: Amostras e pendencias
// ---------------------------------------------------------------------------

@Composable
private fun AmostrasPendenciasContent(
    vm: NovaVisitaViewModel,
    state: NovaVisitaFormState,
    isDesktop: Boolean
) {
    FormDropdown(
        "Foi realizada coleta de amostras?", state.coletaRealizada, vm::onColetaRealizadaChange,
        listOf("Sim", "Nao"), isDesktop = isDesktop
    )
    FormField("Numero de identificacao da amostra", state.numeroAmostra, vm::onNumeroAmostraChange, "Ex: LAB-2025-001234")
    FormField("Quantidade de tubitos coletados", state.quantidadeTubitos, vm::onQuantidadeTubitosChange, keyboardType = KeyboardType.Number)
}

// ---------------------------------------------------------------------------
// Conteudo Secao 5: Observacoes
// ---------------------------------------------------------------------------

@Composable
private fun ObservacoesContent(
    vm: NovaVisitaViewModel,
    state: NovaVisitaFormState
) {
    FormField(
        "Observacoes gerais", state.observacoes, vm::onObservacoesChange,
        "Anote informacoes importantes sobre a visita...", minLines = 4
    )
}

// ---------------------------------------------------------------------------
// Componentes de campo reutilizaveis
// ---------------------------------------------------------------------------

/**
 * Campo de texto com fundo verde claro.
 * Mostra erro embaixo se houver.
 */
@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    minLines: Int = 1,
    hint: String? = null,
    error: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, fontSize = 13.sp, color = TextGray)

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, fontSize = 14.sp, color = LabelGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = if (error != null) Color(0xFFFDE8E8) else FieldGreen,
                unfocusedContainerColor = if (error != null) Color(0xFFFDE8E8) else FieldGreen,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = TextGray,
                unfocusedTextColor = TextGray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            minLines = minLines,
            singleLine = minLines == 1,
            isError = error != null
        )

        if (error != null) {
            Text(text = error, color = StatusError, fontSize = 11.sp)
        }
        if (hint != null) {
            Text(text = hint, fontSize = 11.sp, color = LabelGray, lineHeight = 14.sp)
        }
    }
}

/**
 * Campo dropdown com tamanho controlado.
 * No desktop, dropdowns simples (Sim/Nao) nao esticam a tela inteira.
 * Mostra erro embaixo se houver.
 */
@Composable
private fun FormDropdown(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    placeholder: String = "Selecione",
    hint: String? = null,
    error: String? = null,
    isDesktop: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    // Dropdowns curtos (Sim/Nao, poucos itens) ficam menores no desktop
    val isCompact = options.size <= 3 && isDesktop
    val fieldWidth: Modifier = if (isCompact) Modifier.width(250.dp) else Modifier.fillMaxWidth()

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, fontSize = 13.sp, color = TextGray)

        Box(modifier = fieldWidth) {
            TextField(
                value = value,
                onValueChange = {},
                placeholder = { Text(placeholder, fontSize = 14.sp, color = LabelGray) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().clickable { expanded = true },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (error != null) Color(0xFFFDE8E8) else FieldGreen,
                    unfocusedContainerColor = if (error != null) Color(0xFFFDE8E8) else FieldGreen,
                    disabledContainerColor = if (error != null) Color(0xFFFDE8E8) else FieldGreen,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = TextGray,
                    unfocusedTextColor = TextGray,
                    disabledTextColor = TextGray
                ),
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Expandir", tint = TextGray)
                },
                enabled = false,
                isError = error != null
            )

            // Area clicavel transparente sobre o campo desabilitado
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontSize = 14.sp, color = TextGray) },
                        onClick = { onValueChange(option); expanded = false }
                    )
                }
            }
        }

        if (error != null) {
            Text(text = error, color = StatusError, fontSize = 11.sp)
        }
        if (hint != null) {
            Text(text = hint, fontSize = 11.sp, color = LabelGray, lineHeight = 14.sp)
        }
    }
}

// ---------------------------------------------------------------------------
// Botao GPS
// ---------------------------------------------------------------------------

@Composable
private fun GpsCaptureButton() {
    OutlinedButton(
        onClick = { /* TODO: Capturar GPS */ },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = GeoSaudeColors.Primary
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.5.dp)
    ) {
        Icon(Icons.Default.GpsFixed, contentDescription = "GPS", modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Capturar GPS", fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

// ---------------------------------------------------------------------------
// Helper: converte SectionStatus para cor
// ---------------------------------------------------------------------------

private fun SectionStatus.toColor(): Color = when (this) {
    SectionStatus.EMPTY -> StatusEmpty
    SectionStatus.PARTIAL -> StatusPartial
    SectionStatus.COMPLETE -> StatusComplete
    SectionStatus.ERROR -> StatusError
}