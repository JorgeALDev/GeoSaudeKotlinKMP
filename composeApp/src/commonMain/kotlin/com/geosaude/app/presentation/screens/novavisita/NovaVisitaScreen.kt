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

// Cores baseadas no Figma
private val BackgroundGreen = Color(0xFFE8F5E9)
private val FieldGreen = Color(0xFFD4E8D7)
private val CardWhite = Color.White
private val TextGray = Color(0xFF5F6368)
private val LabelGray = Color(0xFF80868B)

@Composable
fun NovaVisitaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {
        NovaVisitaHeader()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExpandableSection(
                title = "Dados do imóvel",
                initialExpanded = false
            ) {
                InformacoesGeraisContent()
            }

            ExpandableSection(
                title = "Inspeção de depósitos e tratamento realizado",
                initialExpanded = false
            ) {
                InspecaoDepositosContent()
            }

            ExpandableSection(
                title = "Tratamento realizado",
                initialExpanded = false
            ) {
                TratamentoRealizadoContent()
            }

            ExpandableSection(
                title = "Amostras e pendências",
                initialExpanded = false
            ) {
                AmostrasPendenciasContent()
            }

            ExpandableSection(
                title = "Observações",
                initialExpanded = false
            ) {
                ObservacoesContent()
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Enviar formulário */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GeoSaudeColors.Primary
                )
            ) {
                Text("Finalizar e Enviar Visita", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun NovaVisitaHeader() {
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

        OutlinedButton(
            onClick = { /* TODO: Capturar GPS */ },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = GeoSaudeColors.Primary
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.GpsFixed,
                contentDescription = "GPS",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Capturar GPS", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun ExpandableSection(
    title: String,
    initialExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var isExpanded by remember { mutableStateOf(initialExpanded) }

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
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextGray,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Recolher" else "Expandir",
                        tint = GeoSaudeColors.Primary
                    )
                }
            }

            if (isExpanded) {
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = content
                )
            }
        }
    }
}

// Campo de texto estilo Google Forms
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = TextGray,
            fontWeight = FontWeight.Normal
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 14.sp,
                    color = LabelGray
                )
            },
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
            Text(
                text = hint,
                fontSize = 11.sp,
                color = LabelGray,
                lineHeight = 14.sp
            )
        }
    }
}

// Campo de seleção (Dropdown) estilo Google Forms
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

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = TextGray,
            fontWeight = FontWeight.Normal
        )

        Box {
            TextField(
                value = value,
                onValueChange = {},
                placeholder = {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        color = LabelGray
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = FieldGreen,
                    unfocusedContainerColor = FieldGreen,
                    disabledContainerColor = FieldGreen,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = TextGray,
                    unfocusedTextColor = TextGray,
                    disabledTextColor = TextGray
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir",
                        tint = TextGray
                    )
                },
                enabled = false
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                fontSize = 14.sp,
                                color = TextGray
                            )
                        },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (hint != null) {
            Text(
                text = hint,
                fontSize = 11.sp,
                color = LabelGray,
                lineHeight = 14.sp
            )
        }
    }
}

// ==========================================
// 📍 SEÇÃO 1: DADOS DO IMÓVEL
// ==========================================
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
        GoogleFormField(
            label = "Data da visita *",
            value = dataVisita,
            onValueChange = { dataVisita = it },
            placeholder = "DD/MM/AAAA"
        )

        GoogleFormField(
            label = "Código e nome da localidade/bairro *",
            value = codigoLocalidade,
            onValueChange = { codigoLocalidade = it },
            placeholder = "Ex: 001 - Centro"
        )

        GoogleFormField(
            label = "Nome do logradouro (Rua/Av) *",
            value = nomeLogradouro,
            onValueChange = { nomeLogradouro = it },
            placeholder = "Ex: Rua das Acácias"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                GoogleFormField(
                    label = "Número *",
                    value = numero,
                    onValueChange = { numero = it },
                    placeholder = "123",
                    keyboardType = KeyboardType.Number
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                GoogleFormField(
                    label = "Lado do quarteirão",
                    value = ladoQuarteirao,
                    onValueChange = { ladoQuarteirao = it },
                    placeholder = "Ex: Direito"
                )
            }
        }

        GoogleFormField(
            label = "Complemento",
            value = complemento,
            onValueChange = { complemento = it },
            placeholder = "Ex: Apto 201, Casa 2"
        )

        GoogleFormDropdown(
            label = "Tipo de imóvel *",
            value = tipoImovel,
            onValueChange = { tipoImovel = it },
            options = listOf(
                "Residencial",
                "Comércio",
                "Terreno baldio",
                "Ponto estratégico",
                "Estabelecimento de saúde",
                "Escola",
                "Outro"
            )
        )

        GoogleFormField(
            label = "Nome do morador/responsável",
            value = nomeMorador,
            onValueChange = { nomeMorador = it },
            placeholder = "Nome completo"
        )

        GoogleFormField(
            label = "Telefone para contato",
            value = telefone,
            onValueChange = { telefone = it },
            placeholder = "(00) 00000-0000",
            keyboardType = KeyboardType.Phone
        )

        GoogleFormField(
            label = "Horário de entrada no imóvel",
            value = horarioEntrada,
            onValueChange = { horarioEntrada = it },
            placeholder = "HH:MM",
            keyboardType = KeyboardType.Number
        )

        GoogleFormDropdown(
            label = "Tipo de visita",
            value = tipoVisita,
            onValueChange = { tipoVisita = it },
            options = listOf("Normal", "Recuperação", "LIRAa", "Bloqueio"),
            hint = "Normal = Rotina | Recuperação = Reinspeção | LIRAa = Levantamento | Bloqueio = Caso"
        )

        GoogleFormDropdown(
            label = "O imóvel foi inspecionado? *",
            value = inspecionado,
            onValueChange = { inspecionado = it },
            options = listOf("Sim", "Não"),
            hint = "Se NÃO, preencha o motivo da pendência"
        )

        GoogleFormDropdown(
            label = "Motivo da pendência (se não inspecionado)",
            value = motivoPendencia,
            onValueChange = { motivoPendencia = it },
            options = listOf(
                "Nenhuma",
                "Morador recusou",
                "Imóvel fechado",
                "Ausente",
                "Imóvel abandonado",
                "Outro"
            )
        )
    }
}

// ==========================================
// 🦟 SEÇÃO 2: INSPEÇÃO DE DEPÓSITOS
// ==========================================
@Composable
private fun InspecaoDepositosContent() {
    var depositosComFoco by remember { mutableStateOf("") }
    var tipoDeposito by remember { mutableStateOf("") }
    var larvicida by remember { mutableStateOf("") }
    var depositosTratados by remember { mutableStateOf("0") }
    var depositosEliminados by remember { mutableStateOf("0") }
    var depositosA1 by remember { mutableStateOf("0") }
    var depositosA2 by remember { mutableStateOf("0") }
    var depositosB by remember { mutableStateOf("0") }
    var depositosC by remember { mutableStateOf("0") }
    var depositosD1 by remember { mutableStateOf("0") }
    var depositosD2 by remember { mutableStateOf("0") }
    var depositosE by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormDropdown(
            label = "Depósitos com foco encontrados? *",
            value = depositosComFoco,
            onValueChange = { depositosComFoco = it },
            options = listOf("Sim", "Não")
        )

        GoogleFormDropdown(
            label = "Tipo de depósito",
            value = tipoDeposito,
            onValueChange = { tipoDeposito = it },
            options = listOf("A1", "A2", "B", "C", "D1", "D2", "E")
        )

        GoogleFormDropdown(
            label = "Larvicida utilizado",
            value = larvicida,
            onValueChange = { larvicida = it },
            options = listOf(
                "Nenhum",
                "BTI (Bacillus thuringiensis)",
                "Pyriproxyfen",
                "Diflubenzuron",
                "Temefós",
                "Outro"
            )
        )

        GoogleFormField(
            label = "Depósitos tratados",
            value = depositosTratados,
            onValueChange = { depositosTratados = it },
            keyboardType = KeyboardType.Number
        )

        GoogleFormField(
            label = "Depósitos eliminados",
            value = depositosEliminados,
            onValueChange = { depositosEliminados = it },
            keyboardType = KeyboardType.Number
        )

        Text(
            text = "Classificação de depósitos encontrados:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextGray
        )

        GoogleFormField(
            label = "A1 - Água para consumo humano",
            value = depositosA1,
            onValueChange = { depositosA1 = it },
            keyboardType = KeyboardType.Number,
            hint = "Caixa d'água, cisterna, poço, tambor, barril"
        )

        GoogleFormField(
            label = "A2 - Água não destinada ao consumo",
            value = depositosA2,
            onValueChange = { depositosA2 = it },
            keyboardType = KeyboardType.Number,
            hint = "Piscina, aquário, fonte ornamental, bebedouro de animais"
        )

        GoogleFormField(
            label = "B - Móveis",
            value = depositosB,
            onValueChange = { depositosB = it },
            keyboardType = KeyboardType.Number,
            hint = "Vasos de plantas, pratos de vasos, garrafas, pneus"
        )

        GoogleFormField(
            label = "C - Fixos",
            value = depositosC,
            onValueChange = { depositosC = it },
            keyboardType = KeyboardType.Number,
            hint = "Tanques, calhas, lajes, ralos, sanitários"
        )

        GoogleFormField(
            label = "D1 - Naturais",
            value = depositosD1,
            onValueChange = { depositosD1 = it },
            keyboardType = KeyboardType.Number,
            hint = "Bromélias, axilas de folhas, ocos de árvores, bambus"
        )

        GoogleFormField(
            label = "D2 - Artificiais (não utilizáveis)",
            value = depositosD2,
            onValueChange = { depositosD2 = it },
            keyboardType = KeyboardType.Number,
            hint = "Pneus velhos, latas, garrafas abandonadas, entulho"
        )

        GoogleFormField(
            label = "E - Grandes recipientes",
            value = depositosE,
            onValueChange = { depositosE = it },
            keyboardType = KeyboardType.Number,
            hint = "Poços, fossas, caixas de esgoto, drenos"
        )
    }
}

// ==========================================
// 💉 SEÇÃO 3: TRATAMENTO REALIZADO
// ==========================================
@Composable
private fun TratamentoRealizadoContent() {
    var tratamentoAplicado by remember { mutableStateOf("") }
    var tipoLarvicida by remember { mutableStateOf("") }
    var quantidadeLarvicida by remember { mutableStateOf("") }
    var depositosPerifocais by remember { mutableStateOf("0") }
    var cargasAdulticida by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormDropdown(
            label = "Foi aplicado algum tratamento?",
            value = tratamentoAplicado,
            onValueChange = { tratamentoAplicado = it },
            options = listOf("Sim", "Não"),
            hint = "Larvicida, adulticida ou outro produto"
        )

        GoogleFormDropdown(
            label = "Tipo de larvicida utilizado",
            value = tipoLarvicida,
            onValueChange = { tipoLarvicida = it },
            options = listOf(
                "Nenhum",
                "BTI (Bacillus thuringiensis)",
                "Pyriproxyfen",
                "Diflubenzuron",
                "Temefós",
                "Outro"
            )
        )

        GoogleFormField(
            label = "Quantidade de larvicida aplicado (g ou ml)",
            value = quantidadeLarvicida,
            onValueChange = { quantidadeLarvicida = it },
            placeholder = "Ex: 10.5",
            keyboardType = KeyboardType.Decimal
        )

        GoogleFormField(
            label = "Quantidade de depósitos tratados (perifocal)",
            value = depositosPerifocais,
            onValueChange = { depositosPerifocais = it },
            keyboardType = KeyboardType.Number,
            hint = "Depósitos que receberam larvicida"
        )

        GoogleFormField(
            label = "Quantidade de cargas de adulticida aplicadas",
            value = cargasAdulticida,
            onValueChange = { cargasAdulticida = it },
            keyboardType = KeyboardType.Number,
            hint = "Fumacê ou UBV (Ultra Baixo Volume)"
        )
    }
}

// ==========================================
// 🧪 SEÇÃO 4: AMOSTRAS E PENDÊNCIAS
// ==========================================
@Composable
private fun AmostrasPendenciasContent() {
    var coletaRealizada by remember { mutableStateOf("") }
    var numeroAmostra by remember { mutableStateOf("") }
    var quantidadeTubitos by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormDropdown(
            label = "Foi realizada coleta de amostras?",
            value = coletaRealizada,
            onValueChange = { coletaRealizada = it },
            options = listOf("Sim", "Não")
        )

        GoogleFormField(
            label = "Número de identificação da amostra",
            value = numeroAmostra,
            onValueChange = { numeroAmostra = it },
            placeholder = "Ex: LAB-2025-001234"
        )

        GoogleFormField(
            label = "Quantidade de tubitos coletados",
            value = quantidadeTubitos,
            onValueChange = { quantidadeTubitos = it },
            keyboardType = KeyboardType.Number,
            hint = "Tubitos com larvas para análise laboratorial"
        )
    }
}

// ==========================================
// 📝 SEÇÃO 5: OBSERVAÇÕES
// ==========================================
@Composable
private fun ObservacoesContent() {
    var observacoes by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormField(
            label = "Observações gerais",
            value = observacoes,
            onValueChange = { observacoes = it },
            placeholder = "Anote informações importantes sobre a visita...",
            minLines = 4
        )
    }
}