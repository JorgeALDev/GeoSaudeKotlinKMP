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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.components.LogoHeader
import com.geosaude.app.presentation.theme.GeoSaudeColors

// ---------------------------------------------------------------------------
// Cores do formulario Nova Visita, baseadas no Figma
// ---------------------------------------------------------------------------

private val BackgroundGreen = Color(0xFFE8F5E9)
private val FieldGreen = Color(0xFFD4E8D7)
private val CardWhite = Color.White
private val TextGray = Color(0xFF5F6368)
private val LabelGray = Color(0xFF80868B)

// Status de preenchimento das secoes
private val StatusEmpty = Color(0xFFE0E0E0)       // Cinza - nao iniciada
private val StatusPartial = Color(0xFFFFA726)      // Laranja - preenchimento parcial
private val StatusComplete = Color(0xFF4CAF50)     // Verde - completa
private val StatusError = Color(0xFFEF5350)        // Vermelho - campo obrigatorio faltando

// ---------------------------------------------------------------------------
// Ponto de entrada: detecta mobile/desktop para ajustar padding e header
// ---------------------------------------------------------------------------

/**
 * Tela de Nova Visita - conteudo puro.
 *
 * NAO contem menu de navegacao (BottomNav ou Sidebar).
 * A navegacao e gerenciada pelo MainScreen que envolve esta tela.
 *
 * Mobile: header com logo + GPS, padding compacto.
 * Desktop: header com titulo + subtitulo + GPS, padding maior.
 */
@Composable
fun NovaVisitaScreen() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth >= 800.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGreen)
        ) {
            // Header diferente por plataforma
            if (isDesktop) {
                DesktopHeader()
            } else {
                MobileHeader()
            }

            // Conteudo das secoes (compartilhado, so muda padding)
            SectionsList(isDesktop = isDesktop)
        }
    }
}

// ---------------------------------------------------------------------------
// Headers
// ---------------------------------------------------------------------------

/**
 * Header mobile: logo "GeoSaude" a esquerda, botao "Capturar GPS" a direita.
 */
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
        LogoHeader(
            backgroundColor = BackgroundGreen,
            modifier = Modifier.weight(1f)
        )
        GpsCaptureButton()
    }
}

/**
 * Header desktop: "Nova visita" + subtitulo + botao "Capturar GPS".
 */
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
            Text(
                text = "Nova visita",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GeoSaudeColors.TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Preencha os dados da inspecao",
                fontSize = 14.sp,
                color = LabelGray
            )
        }
        GpsCaptureButton()
    }
}

// ---------------------------------------------------------------------------
// Lista de secoes expandiveis
// ---------------------------------------------------------------------------

/**
 * Lista de secoes expandiveis com scroll.
 * Padding ajustado conforme mobile ou desktop.
 */
@Composable
private fun SectionsList(isDesktop: Boolean) {
    val horizontalPadding = if (isDesktop) 32.dp else 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontalPadding, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExpandableSection(title = "Dados do imovel") {
            InformacoesGeraisContent()
        }

        ExpandableSection(title = "Inspecao de depositos e tratamento realizado") {
            InspecaoDepositosContent()
        }

        ExpandableSection(title = "Tratamento realizado") {
            TratamentoRealizadoContent()
        }

        ExpandableSection(title = "Amostras e pendencias") {
            AmostrasPendenciasContent()
        }

        ExpandableSection(title = "Observacoes") {
            ObservacoesContent()
        }

        Spacer(modifier = Modifier.height(16.dp))
        SubmitButton()
        Spacer(modifier = Modifier.height(if (isDesktop) 24.dp else 16.dp))
    }
}

// ---------------------------------------------------------------------------
// Componentes compartilhados
// ---------------------------------------------------------------------------

/** Botao "Capturar GPS". */
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

/** Botao "Finalizar e Enviar Visita". */
@Composable
private fun SubmitButton() {
    Button(
        onClick = { /* TODO: Enviar formulario */ },
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GeoSaudeColors.Primary)
    ) {
        Text("Finalizar e Enviar Visita", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

// ---------------------------------------------------------------------------
// Secao expandivel com indicador de status
// ---------------------------------------------------------------------------

/**
 * Card expandivel com titulo, seta e indicador de status colorido.
 * O indicador muda de cor conforme o preenchimento:
 * - Cinza: nao iniciada
 * - Laranja: preenchimento parcial
 * - Verde: completa
 * - Vermelho: campo obrigatorio faltando
 *
 * @param title Titulo da secao.
 * @param status Cor do indicador de status (default: cinza = nao iniciada).
 * @param content Conteudo renderizado quando expandido.
 */
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
                // Indicador colorido de status da secao
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(status, RoundedCornerShape(4.dp))
                )

                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextGray,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
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
// Campos de formulario
// ---------------------------------------------------------------------------

/** Campo de texto com fundo verde claro. */
@Composable
private fun GoogleFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    minLines: Int = 1,
    hint: String? = null
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
                focusedContainerColor = FieldGreen,
                unfocusedContainerColor = FieldGreen,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = TextGray,
                unfocusedTextColor = TextGray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            minLines = minLines,
            singleLine = minLines == 1
        )
        if (hint != null) {
            Text(text = hint, fontSize = 11.sp, color = LabelGray, lineHeight = 14.sp)
        }
    }
}

/** Campo dropdown com fundo verde claro. */
@Composable
private fun GoogleFormDropdown(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    placeholder: String = "Selecione",
    hint: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, fontSize = 13.sp, color = TextGray)
        Box {
            TextField(
                value = value, onValueChange = {},
                placeholder = { Text(placeholder, fontSize = 14.sp, color = LabelGray) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().clickable { expanded = true },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = FieldGreen, unfocusedContainerColor = FieldGreen,
                    disabledContainerColor = FieldGreen,
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = TextGray, unfocusedTextColor = TextGray,
                    disabledTextColor = TextGray
                ),
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Expandir", tint = TextGray)
                },
                enabled = false
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f).background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontSize = 14.sp, color = TextGray) },
                        onClick = { onValueChange(option); expanded = false }
                    )
                }
            }
        }
        if (hint != null) {
            Text(text = hint, fontSize = 11.sp, color = LabelGray, lineHeight = 14.sp)
        }
    }
}

// ---------------------------------------------------------------------------
// Conteudo das secoes
// ---------------------------------------------------------------------------

@Composable
private fun InformacoesGeraisContent() {
    var dataVisita by remember { mutableStateOf("") }
    var codigoLocalidade by remember { mutableStateOf("") }
    var nomeLogradouro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var ladoQuarteirao by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var tipoImovel by remember { mutableStateOf("") }
    var nomeMorador by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var horarioEntrada by remember { mutableStateOf("") }
    var tipoVisita by remember { mutableStateOf("") }
    var inspecionado by remember { mutableStateOf("") }
    var motivoPendencia by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormField("Data da visita *", dataVisita, { dataVisita = it }, "DD/MM/AAAA")
        GoogleFormField("Codigo e nome da localidade/bairro *", codigoLocalidade, { codigoLocalidade = it }, "Ex: 001 - Centro")
        GoogleFormField("Nome do logradouro (Rua/Av) *", nomeLogradouro, { nomeLogradouro = it }, "Ex: Rua das Acacias")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                GoogleFormField("Numero *", numero, { numero = it }, "123", KeyboardType.Number)
            }
            Column(modifier = Modifier.weight(1f)) {
                GoogleFormField("Lado do quarteirao", ladoQuarteirao, { ladoQuarteirao = it }, "Ex: Direito")
            }
        }
        GoogleFormField("Complemento", complemento, { complemento = it }, "Ex: Apto 201, Casa 2")
        GoogleFormDropdown("Tipo de imovel *", tipoImovel, { tipoImovel = it },
            listOf("Residencial", "Comercio", "Terreno baldio", "Ponto estrategico", "Escola", "Outro"))
        GoogleFormField("Nome do morador/responsavel", nomeMorador, { nomeMorador = it }, "Nome completo")
        GoogleFormField("Telefone para contato", telefone, { telefone = it }, "(00) 00000-0000", KeyboardType.Phone)
        GoogleFormField("Horario de entrada no imovel", horarioEntrada, { horarioEntrada = it }, "HH:MM", KeyboardType.Number)
        GoogleFormDropdown("Tipo de visita", tipoVisita, { tipoVisita = it },
            listOf("Normal", "Recuperacao", "LIRAa", "Bloqueio"),
            hint = "Normal = Rotina | Recuperacao = Reinspecao")
        GoogleFormDropdown("O imovel foi inspecionado? *", inspecionado, { inspecionado = it }, listOf("Sim", "Nao"),
            hint = "Se NAO, preencha o motivo da pendencia")
        GoogleFormDropdown("Motivo da pendencia", motivoPendencia, { motivoPendencia = it },
            listOf("Nenhuma", "Morador recusou", "Imovel fechado", "Ausente", "Imovel abandonado", "Outro"))
    }
}

@Composable
private fun InspecaoDepositosContent() {
    var depositosComFoco by remember { mutableStateOf("") }
    var tipoDeposito by remember { mutableStateOf("") }
    var larvicida by remember { mutableStateOf("") }
    var depositosTratados by remember { mutableStateOf("0") }
    var depositosEliminados by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormDropdown("Depositos com foco encontrados? *", depositosComFoco, { depositosComFoco = it }, listOf("Sim", "Nao"))
        GoogleFormDropdown("Tipo de deposito", tipoDeposito, { tipoDeposito = it }, listOf("A1", "A2", "B", "C", "D1", "D2", "E"))
        GoogleFormDropdown("Larvicida utilizado", larvicida, { larvicida = it },
            listOf("Nenhum", "BTI", "Pyriproxyfen", "Diflubenzuron", "Temefos", "Outro"))
        GoogleFormField("Depositos tratados", depositosTratados, { depositosTratados = it }, keyboardType = KeyboardType.Number)
        GoogleFormField("Depositos eliminados", depositosEliminados, { depositosEliminados = it }, keyboardType = KeyboardType.Number)
    }
}

@Composable
private fun TratamentoRealizadoContent() {
    var tratamentoAplicado by remember { mutableStateOf("") }
    var tipoLarvicida by remember { mutableStateOf("") }
    var quantidadeLarvicida by remember { mutableStateOf("") }
    var depositosPerifocais by remember { mutableStateOf("0") }
    var cargasAdulticida by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormDropdown("Foi aplicado algum tratamento?", tratamentoAplicado, { tratamentoAplicado = it }, listOf("Sim", "Nao"))
        GoogleFormDropdown("Tipo de larvicida utilizado", tipoLarvicida, { tipoLarvicida = it },
            listOf("Nenhum", "BTI", "Pyriproxyfen", "Diflubenzuron", "Temefos", "Outro"))
        GoogleFormField("Quantidade de larvicida (g ou ml)", quantidadeLarvicida, { quantidadeLarvicida = it }, "Ex: 10.5", KeyboardType.Decimal)
        GoogleFormField("Depositos tratados (perifocal)", depositosPerifocais, { depositosPerifocais = it }, keyboardType = KeyboardType.Number)
        GoogleFormField("Cargas de adulticida aplicadas", cargasAdulticida, { cargasAdulticida = it }, keyboardType = KeyboardType.Number)
    }
}

@Composable
private fun AmostrasPendenciasContent() {
    var coletaRealizada by remember { mutableStateOf("") }
    var numeroAmostra by remember { mutableStateOf("") }
    var quantidadeTubitos by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormDropdown("Foi realizada coleta de amostras?", coletaRealizada, { coletaRealizada = it }, listOf("Sim", "Nao"))
        GoogleFormField("Numero de identificacao da amostra", numeroAmostra, { numeroAmostra = it }, "Ex: LAB-2025-001234")
        GoogleFormField("Quantidade de tubitos coletados", quantidadeTubitos, { quantidadeTubitos = it }, keyboardType = KeyboardType.Number)
    }
}

@Composable
private fun ObservacoesContent() {
    var observacoes by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormField("Observacoes gerais", observacoes, { observacoes = it },
            "Anote informacoes importantes sobre a visita...", minLines = 4)
    }
}