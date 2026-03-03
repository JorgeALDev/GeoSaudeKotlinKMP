package com.geosaude.app.presentation.screens.novavisita

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.components.LogoHeader
import com.geosaude.app.presentation.theme.GeoSaudeColors
import geosaude.composeapp.generated.resources.Res
import geosaude.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

// ---------------------------------------------------------------------------
// Cores do formulario Nova Visita, baseadas no design system Figma
// ---------------------------------------------------------------------------

private val BackgroundGreen = Color(0xFFE8F5E9)
private val FieldGreen = Color(0xFFD4E8D7)
private val CardWhite = Color.White
private val TextGray = Color(0xFF5F6368)
private val LabelGray = Color(0xFF80868B)
private val SidebarGreen = Color(0xFF2D5F3E)

// ---------------------------------------------------------------------------
// Ponto de entrada: detecta largura e delega para Mobile ou Desktop
// ---------------------------------------------------------------------------

/**
 * Tela de Nova Visita com layout responsivo.
 * Breakpoint de 800dp para alternar entre Mobile e Desktop.
 *
 * Desktop (Frame 25/28 do Figma):
 * - Sidebar fixa a esquerda com logo, menu de navegacao e logout.
 * - Area principal a direita com header "Nova visita" + subtitulo + botao GPS.
 * - Secoes expandiveis em cards brancos sobre fundo verde claro.
 *
 * Mobile (Figma Mobile):
 * - Header compacto com logo "GeoSaude" e botao "Capturar GPS".
 * - Secoes expandiveis empilhadas verticalmente.
 * - Espaco inferior reservado para a BottomNavigationBar do app.
 */
@Composable
fun NovaVisitaScreen() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth >= 800.dp

        if (isDesktop) {
            NovaVisitaDesktop()
        } else {
            NovaVisitaMobile()
        }
    }
}

// ===========================================================================
//
//  LAYOUT MOBILE (Figma Mobile)
//
//  Estrutura:
//  - Header: Logo "GeoSaude" + botao "Capturar GPS"
//  - Body: secoes expandiveis em lista vertical com scroll
//  - Espaco inferior para a BottomNavigationBar
//
// ===========================================================================

@Composable
private fun NovaVisitaMobile() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {
        // Header mobile: logo + botao GPS
        MobileHeader()

        // Lista de secoes expandiveis com scroll
        MobileSectionsList()
    }
}

/**
 * Header mobile conforme Figma:
 * Logo "GeoSaude" a esquerda, botao "Capturar GPS" a direita.
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
 * Corpo do layout mobile: secoes expandiveis empilhadas verticalmente.
 * Inclui espaco inferior para nao sobrepor a BottomNavigationBar.
 */
@Composable
private fun MobileSectionsList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
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

        // Botao de envio do formulario
        SubmitButton()

        // Espaco inferior para a BottomNavigationBar
        Spacer(modifier = Modifier.height(80.dp))
    }
}

// ===========================================================================
//
//  LAYOUT DESKTOP (Frame 25/28 do Figma)
//
//  Estrutura:
//  - Sidebar fixa a esquerda (240dp):
//      - Logo + "GeoSaude" no topo
//      - Menu: Painel, Nova visita (selecionado), Relatorios, Historico
//      - Logout no rodape
//  - Area principal a direita:
//      - Header: "Nova visita" + subtitulo + botao "Capturar GPS"
//      - Secoes expandiveis em cards brancos
//
// ===========================================================================

@Composable
private fun NovaVisitaDesktop() {
    Row(modifier = Modifier.fillMaxSize()) {
        // Sidebar fixa lateral esquerda
        DesktopSidebar()

        // Area principal de conteudo
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(BackgroundGreen)
        ) {
            // Header com titulo, subtitulo e botao GPS
            DesktopHeader()

            // Secoes expandiveis com scroll
            DesktopSectionsList()
        }
    }
}

/**
 * Header desktop conforme Frame 25 do Figma:
 * "Nova visita" (titulo) + "Preencha os dados da inspecao" (subtitulo)
 * + botao "Capturar GPS" alinhado a direita.
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

/**
 * Corpo do layout desktop: secoes expandiveis com padding maior
 * e sem espaco inferior para BottomNav (nao existe no desktop).
 */
@Composable
private fun DesktopSectionsList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp, vertical = 16.dp),
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

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ---------------------------------------------------------------------------
// Sidebar Desktop (Frame 25 do Figma)
// ---------------------------------------------------------------------------

/**
 * Sidebar fixa lateral conforme Frame 25 do Figma:
 * - Topo: logo + "GeoSaude"
 * - Menu: Painel, Nova visita (selecionado com destaque verde), Relatorios, Historico
 * - Rodape: botao Logout
 * - Fundo verde escuro, largura fixa 200dp
 */
@Composable
private fun DesktopSidebar() {
    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(SidebarGreen)
            .padding(vertical = 20.dp)
    ) {
        // Logo e nome no topo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo GeoSaude",
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "GeoSaude",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Itens de navegacao
        SidebarItem(
            icon = Icons.Default.Dashboard,
            label = "Painel",
            isSelected = false,
            onClick = { /* TODO: Navegar para Painel */ }
        )

        SidebarItem(
            icon = Icons.Default.Assignment,
            label = "Nova visita",
            isSelected = true,
            onClick = { /* Ja esta nesta tela */ }
        )

        SidebarItem(
            icon = Icons.Default.Description,
            label = "Relatorios",
            isSelected = false,
            onClick = { /* TODO: Navegar para Relatorios */ }
        )

        SidebarItem(
            icon = Icons.Default.Schedule,
            label = "Historico",
            isSelected = false,
            onClick = { /* TODO: Navegar para Historico */ }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Logout no rodape
        SidebarItem(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            label = "Logout",
            isSelected = false,
            onClick = { /* TODO: Executar logout */ }
        )
    }
}

/**
 * Item individual do menu lateral.
 * Quando selecionado, exibe fundo verde claro com texto branco.
 * Quando nao selecionado, exibe texto branco com opacidade reduzida.
 *
 * @param icon Icone Material do item.
 * @param label Texto do item.
 * @param isSelected Se este item esta ativo.
 * @param onClick Callback ao clicar.
 */
@Composable
private fun SidebarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) GeoSaudeColors.Primary else Color.Transparent
    val textColor = Color.White.copy(alpha = if (isSelected) 1f else 0.7f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .background(bgColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = textColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = textColor
        )
    }
}

// ---------------------------------------------------------------------------
// Componentes compartilhados entre Mobile e Desktop
// ---------------------------------------------------------------------------

/**
 * Botao "Capturar GPS" usado tanto no header mobile quanto no desktop.
 */
@Composable
private fun GpsCaptureButton() {
    OutlinedButton(
        onClick = { /* TODO: Capturar coordenadas GPS */ },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = GeoSaudeColors.Primary
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.GpsFixed,
            contentDescription = "Capturar localizacao GPS",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Capturar GPS", fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

/**
 * Botao de envio do formulario "Finalizar e Enviar Visita".
 */
@Composable
private fun SubmitButton() {
    Button(
        onClick = { /* TODO: Validar e enviar formulario */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GeoSaudeColors.Primary
        )
    ) {
        Text(
            text = "Finalizar e Enviar Visita",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ---------------------------------------------------------------------------
// Secao expandivel (card branco com seta de expansao)
// ---------------------------------------------------------------------------

/**
 * Card expandivel com titulo e seta.
 * Conforme Figma: card branco com cantos arredondados,
 * titulo a esquerda, seta verde a direita.
 * Quando expandido, mostra conteudo abaixo de um divisor.
 *
 * @param title Titulo da secao.
 * @param content Conteudo renderizado quando expandido.
 */
@Composable
private fun ExpandableSection(
    title: String,
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
            // Cabecalho clicavel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
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

                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Recolher secao" else "Expandir secao",
                    tint = GeoSaudeColors.Primary
                )
            }

            // Conteudo expandido
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
// Campos de formulario reutilizaveis
// ---------------------------------------------------------------------------

/**
 * Campo de texto com fundo verde claro, sem borda inferior.
 * Visual estilo Google Forms conforme Figma.
 *
 * @param label Rotulo acima do campo.
 * @param value Valor atual.
 * @param onValueChange Callback de alteracao.
 * @param placeholder Texto placeholder.
 * @param keyboardType Tipo de teclado.
 * @param minLines Linhas minimas (> 1 para multilinhas).
 * @param hint Texto auxiliar abaixo do campo.
 */
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
                Text(text = placeholder, fontSize = 14.sp, color = LabelGray)
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
            Text(text = hint, fontSize = 11.sp, color = LabelGray, lineHeight = 14.sp)
        }
    }
}

/**
 * Campo dropdown com fundo verde claro.
 * Usa Box + DropdownMenu para compatibilidade KMP.
 *
 * @param label Rotulo acima do campo.
 * @param value Opcao selecionada.
 * @param onValueChange Callback quando uma opcao e selecionada.
 * @param options Lista de opcoes do dropdown.
 * @param placeholder Texto quando nenhuma opcao esta selecionada.
 * @param hint Texto auxiliar abaixo do campo.
 */
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
                    Text(text = placeholder, fontSize = 14.sp, color = LabelGray)
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
                        contentDescription = "Abrir lista de opcoes",
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
                        text = { Text(text = option, fontSize = 14.sp, color = TextGray) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
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
// Secao 1: Dados do imovel
// ---------------------------------------------------------------------------

/**
 * Conteudo da secao "Dados do imovel" com todos os campos do formulario.
 * Estado local por secao (cada campo gerenciado via mutableStateOf).
 */
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
            label = "Codigo e nome da localidade/bairro *",
            value = codigoLocalidade,
            onValueChange = { codigoLocalidade = it },
            placeholder = "Ex: 001 - Centro"
        )

        GoogleFormField(
            label = "Nome do logradouro (Rua/Av) *",
            value = nomeLogradouro,
            onValueChange = { nomeLogradouro = it },
            placeholder = "Ex: Rua das Acacias"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                GoogleFormField(
                    label = "Numero *",
                    value = numero,
                    onValueChange = { numero = it },
                    placeholder = "123",
                    keyboardType = KeyboardType.Number
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                GoogleFormField(
                    label = "Lado do quarteirao",
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
            label = "Tipo de imovel *",
            value = tipoImovel,
            onValueChange = { tipoImovel = it },
            options = listOf(
                "Residencial", "Comercio", "Terreno baldio",
                "Ponto estrategico", "Estabelecimento de saude",
                "Escola", "Outro"
            )
        )

        GoogleFormField(
            label = "Nome do morador/responsavel",
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
            label = "Horario de entrada no imovel",
            value = horarioEntrada,
            onValueChange = { horarioEntrada = it },
            placeholder = "HH:MM",
            keyboardType = KeyboardType.Number
        )

        GoogleFormDropdown(
            label = "Tipo de visita",
            value = tipoVisita,
            onValueChange = { tipoVisita = it },
            options = listOf("Normal", "Recuperacao", "LIRAa", "Bloqueio"),
            hint = "Normal = Rotina | Recuperacao = Reinspecao | LIRAa = Levantamento | Bloqueio = Caso"
        )

        GoogleFormDropdown(
            label = "O imovel foi inspecionado? *",
            value = inspecionado,
            onValueChange = { inspecionado = it },
            options = listOf("Sim", "Nao"),
            hint = "Se NAO, preencha o motivo da pendencia"
        )

        GoogleFormDropdown(
            label = "Motivo da pendencia (se nao inspecionado)",
            value = motivoPendencia,
            onValueChange = { motivoPendencia = it },
            options = listOf(
                "Nenhuma", "Morador recusou", "Imovel fechado",
                "Ausente", "Imovel abandonado", "Outro"
            )
        )
    }
}

// ---------------------------------------------------------------------------
// Secao 2: Inspecao de depositos
// ---------------------------------------------------------------------------

/**
 * Conteudo da secao "Inspecao de depositos e tratamento realizado".
 * Inclui campos para foco, tipo de deposito, larvicida e classificacao A1-E.
 */
@Composable
private fun InspecaoDepositosContent() {
    var depositosComFoco by remember { mutableStateOf("") }
    var tipoDeposito by remember { mutableStateOf("") }
    var larvicida by remember { mutableStateOf("") }
    var depositosTratados by remember { mutableStateOf("0") }
    var depositosEliminados by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormDropdown(
            label = "Depositos com foco encontrados? *",
            value = depositosComFoco,
            onValueChange = { depositosComFoco = it },
            options = listOf("Sim", "Nao")
        )

        GoogleFormDropdown(
            label = "Tipo de deposito",
            value = tipoDeposito,
            onValueChange = { tipoDeposito = it },
            options = listOf("A1", "A2", "B", "C", "D1", "D2", "E")
        )

        GoogleFormDropdown(
            label = "Larvicida utilizado",
            value = larvicida,
            onValueChange = { larvicida = it },
            options = listOf(
                "Nenhum", "BTI (Bacillus thuringiensis)",
                "Pyriproxyfen", "Diflubenzuron", "Temefos", "Outro"
            )
        )

        GoogleFormField(
            label = "Depositos tratados",
            value = depositosTratados,
            onValueChange = { depositosTratados = it },
            keyboardType = KeyboardType.Number
        )

        GoogleFormField(
            label = "Depositos eliminados",
            value = depositosEliminados,
            onValueChange = { depositosEliminados = it },
            keyboardType = KeyboardType.Number
        )
    }
}

// ---------------------------------------------------------------------------
// Secao 3: Tratamento realizado
// ---------------------------------------------------------------------------

/**
 * Conteudo da secao "Tratamento realizado".
 */
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
            options = listOf("Sim", "Nao"),
            hint = "Larvicida, adulticida ou outro produto"
        )

        GoogleFormDropdown(
            label = "Tipo de larvicida utilizado",
            value = tipoLarvicida,
            onValueChange = { tipoLarvicida = it },
            options = listOf(
                "Nenhum", "BTI (Bacillus thuringiensis)",
                "Pyriproxyfen", "Diflubenzuron", "Temefos", "Outro"
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
            label = "Quantidade de depositos tratados (perifocal)",
            value = depositosPerifocais,
            onValueChange = { depositosPerifocais = it },
            keyboardType = KeyboardType.Number
        )

        GoogleFormField(
            label = "Quantidade de cargas de adulticida aplicadas",
            value = cargasAdulticida,
            onValueChange = { cargasAdulticida = it },
            keyboardType = KeyboardType.Number,
            hint = "Fumace ou UBV (Ultra Baixo Volume)"
        )
    }
}

// ---------------------------------------------------------------------------
// Secao 4: Amostras e pendencias
// ---------------------------------------------------------------------------

/**
 * Conteudo da secao "Amostras e pendencias".
 */
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
            options = listOf("Sim", "Nao")
        )

        GoogleFormField(
            label = "Numero de identificacao da amostra",
            value = numeroAmostra,
            onValueChange = { numeroAmostra = it },
            placeholder = "Ex: LAB-2025-001234"
        )

        GoogleFormField(
            label = "Quantidade de tubitos coletados",
            value = quantidadeTubitos,
            onValueChange = { quantidadeTubitos = it },
            keyboardType = KeyboardType.Number,
            hint = "Tubitos com larvas para analise laboratorial"
        )
    }
}

// ---------------------------------------------------------------------------
// Secao 5: Observacoes
// ---------------------------------------------------------------------------

/**
 * Conteudo da secao "Observacoes".
 * Campo de texto livre multilinha.
 */
@Composable
private fun ObservacoesContent() {
    var observacoes by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GoogleFormField(
            label = "Observacoes gerais",
            value = observacoes,
            onValueChange = { observacoes = it },
            placeholder = "Anote informacoes importantes sobre a visita...",
            minLines = 4
        )
    }
}