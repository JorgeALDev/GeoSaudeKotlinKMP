package com.geosaude.app.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.components.IllustrationSection
import com.geosaude.app.presentation.components.LogoHeader
import com.geosaude.app.presentation.theme.GeoSaudeColors

// ---------------------------------------------------------------------------
// Ponto de entrada: detecta largura e delega para layout Mobile ou Desktop
// ---------------------------------------------------------------------------

/**
 * Tela de login com layout responsivo.
 * Breakpoint de 800dp para alternar entre Mobile e Desktop.
 *
 * @param onNavigateToCadastro Navega para tela de cadastro.
 * @param onNavigateToRecuperarSenha Navega para recuperacao de senha.
 * @param onLoginSuccess Executado apos login bem-sucedido, recebe a funcao do usuario.
 */
@Composable
fun LoginScreen(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth >= 800.dp

        if (isDesktop) {
            LoginScreenDesktop(
                onNavigateToCadastro = onNavigateToCadastro,
                onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                onLoginSuccess = onLoginSuccess
            )
        } else {
            LoginScreenMobile(
                onNavigateToCadastro = onNavigateToCadastro,
                onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                onLoginSuccess = onLoginSuccess
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Desktop (Frame 2 do Figma)
// ---------------------------------------------------------------------------

/**
 * Layout desktop conforme Frame 2 do Figma:
 * - Esquerda: fundo verde com logo no topo + ilustracao centralizada (sem textos).
 * - Direita: fundo branco com card contendo o formulario de login.
 * - Card sem borda, apenas sombra suave.
 * - "Esqueceu sua senha?" fica ABAIXO do botao "Entrar".
 */
@Composable
private fun LoginScreenDesktop(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Metade esquerda: logo sobre ilustracao, sem textos sobrepostos
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.Background)
        ) {
            // Ilustracao centralizada (sem textos conforme Figma)
            IllustrationSection()

            // Logo "GeoSaude" no canto superior esquerdo sobre a ilustracao
            LogoHeader(
                backgroundColor = GeoSaudeColors.Background,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }

        // Metade direita: formulario de login
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.White)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .fillMaxWidth(0.85f)
                        .wrapContentHeight()
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = GeoSaudeColors.Primary.copy(alpha = 0.15f)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GeoSaudeColors.CardBackground
                    )
                ) {
                    LoginFormDesktop(
                        onNavigateToCadastro = onNavigateToCadastro,
                        onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                        onLoginSuccess = onLoginSuccess
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Mobile (Figma Login Mobile)
// ---------------------------------------------------------------------------

/**
 * Layout mobile conforme Figma:
 * - Logo "GeoSaude" no topo com fundo verde.
 * - Card branco arredondado com formulario.
 * - Inclui campo "Qual sua funcao?" (dropdown) conforme Figma mobile.
 * - "Esqueceu sua senha?" abaixo do botao.
 */
@Composable
private fun LoginScreenMobile(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeoSaudeColors.Background)
    ) {
        LogoHeader(backgroundColor = GeoSaudeColors.Background)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = GeoSaudeColors.Primary.copy(alpha = 0.1f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = GeoSaudeColors.CardBackground
                )
            ) {
                LoginFormMobile(
                    onNavigateToCadastro = onNavigateToCadastro,
                    onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                    onLoginSuccess = onLoginSuccess
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Formulario Desktop (sem campo funcao, "Esqueceu senha?" apos botao)
// ---------------------------------------------------------------------------

/**
 * Formulario de login para desktop conforme Frame 2 do Figma.
 * Campos: matricula, senha.
 * Ordem: cabecalho > matricula > senha > botao Entrar > "Esqueceu sua senha?"
 */
@Composable
private fun LoginFormDesktop(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    val viewModel = org.koin.compose.koinInject<LoginViewModel>()
    val formState by viewModel.formState.collectAsState()

    var senhaVisivel by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 48.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Cabecalho: "Bem vindo ao GeoSaude" + "Login" + link cadastro
        LoginFormHeader(onNavigateToCadastro = onNavigateToCadastro)

        // Campo de matricula
        FormTextField(
            label = "Coloque seu e-mail ou matricula",
            value = formState.matricula,
            onValueChange = { viewModel.onMatriculaChange(it) },
            placeholder = "E-mail ou matricula",
            error = formState.matriculaError
        )

        // Campo de senha
        PasswordField(
            label = "Coloque sua senha",
            value = formState.senha,
            onValueChange = { viewModel.onSenhaChange(it) },
            placeholder = "Senha",
            isVisible = senhaVisivel,
            onVisibilityToggle = { senhaVisivel = !senhaVisivel },
            error = formState.senhaError
        )

        // Erro geral de autenticacao
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = GeoSaudeColors.Error,
                fontSize = 13.sp
            )
        }

        // Botao "Entrar"
        LoginButton(
            isLoading = formState.isLoading,
            onClick = {
                errorMessage = null
                viewModel.onLogin(
                    onSuccess = onLoginSuccess,
                    onError = { errorMessage = it }
                )
            }
        )

        // "Esqueceu sua senha?" - ABAIXO do botao conforme Figma
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Esqueceu sua senha?",
                fontSize = 13.sp,
                color = GeoSaudeColors.Primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onNavigateToRecuperarSenha() }
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Formulario Mobile (com campo funcao, "Esqueceu senha?" apos botao)
// ---------------------------------------------------------------------------

/**
 * Formulario de login para mobile conforme Figma Mobile.
 * Inclui campo "Qual sua funcao?" com dropdown alem de matricula e senha.
 * Ordem: cabecalho > matricula > senha > funcao > "Esqueceu senha?" > botao Entrar
 */
@Composable
private fun LoginFormMobile(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    val viewModel = org.koin.compose.koinInject<LoginViewModel>()
    val formState by viewModel.formState.collectAsState()

    var senhaVisivel by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showFuncaoDropdown by remember { mutableStateOf(false) }
    var funcaoSelecionada by remember { mutableStateOf("") }

    val funcoes = listOf("Agente de Campo", "Supervisor")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cabecalho compacto
        LoginFormHeader(onNavigateToCadastro = onNavigateToCadastro)

        // Campo de matricula
        FormTextField(
            label = "Coloque seu e-mail ou matricula",
            value = formState.matricula,
            onValueChange = { viewModel.onMatriculaChange(it) },
            placeholder = "E-mail ou matricula",
            error = formState.matriculaError
        )

        // Campo de senha
        PasswordField(
            label = "Coloque sua senha",
            value = formState.senha,
            onValueChange = { viewModel.onSenhaChange(it) },
            placeholder = "Senha",
            isVisible = senhaVisivel,
            onVisibilityToggle = { senhaVisivel = !senhaVisivel },
            error = formState.senhaError
        )

        // Campo "Qual sua funcao?" - presente apenas no mobile conforme Figma
        DropdownField(
            label = "Qual sua funcao?",
            value = funcaoSelecionada,
            options = funcoes,
            expanded = showFuncaoDropdown,
            onExpandedChange = { showFuncaoDropdown = it },
            onValueChange = {
                funcaoSelecionada = it
                showFuncaoDropdown = false
            },
            placeholder = "-"
        )

        // "Esqueceu sua senha?" alinhado a direita
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Esqueceu sua senha?",
                fontSize = 12.sp,
                color = GeoSaudeColors.Primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onNavigateToRecuperarSenha() }
            )
        }

        // Erro geral
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = GeoSaudeColors.Error,
                fontSize = 12.sp
            )
        }

        // Botao "Entrar"
        LoginButton(
            isLoading = formState.isLoading,
            onClick = {
                errorMessage = null
                viewModel.onLogin(
                    onSuccess = onLoginSuccess,
                    onError = { errorMessage = it }
                )
            }
        )
    }
}

// ---------------------------------------------------------------------------
// Componentes reutilizaveis
// ---------------------------------------------------------------------------

/**
 * Cabecalho do formulario de login.
 * Linha 1: "Bem vindo ao GeoSaude"
 * Linha 2: "Login" (grande) + "Nao tem conta? Cadastre-se" (alinhado a direita)
 */
@Composable
private fun LoginFormHeader(onNavigateToCadastro: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append("Bem vindo ao ")
                withStyle(
                    SpanStyle(
                        color = GeoSaudeColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("GeoSaude")
                }
            },
            fontSize = 15.sp,
            color = GeoSaudeColors.TextPrimary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = GeoSaudeColors.TextPrimary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Nao tem conta?\n",
                    fontSize = 12.sp,
                    color = GeoSaudeColors.TextSecondary
                )
                Text(
                    text = "Cadastre-se",
                    fontSize = 12.sp,
                    color = GeoSaudeColors.Primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToCadastro() }
                )
            }
        }
    }
}

/**
 * Campo de texto reutilizavel com label, placeholder e erro opcional.
 *
 * @param label Rotulo acima do campo.
 * @param value Valor atual.
 * @param onValueChange Callback de alteracao.
 * @param placeholder Texto placeholder.
 * @param error Mensagem de erro; null se valido.
 */
@Composable
private fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: String? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = GeoSaudeColors.TextPrimary,
            fontWeight = FontWeight.Medium
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder, color = GeoSaudeColors.Gray400, fontSize = 14.sp)
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GeoSaudeColors.Primary,
                unfocusedBorderColor = GeoSaudeColors.InputBorder,
                focusedContainerColor = GeoSaudeColors.White,
                unfocusedContainerColor = GeoSaudeColors.White
            ),
            isError = error != null
        )

        if (error != null) {
            Text(text = error, color = GeoSaudeColors.Error, fontSize = 11.sp)
        }
    }
}

/**
 * Campo de senha com toggle de visibilidade.
 *
 * @param label Rotulo acima do campo.
 * @param value Valor atual da senha.
 * @param onValueChange Callback de alteracao.
 * @param placeholder Texto placeholder.
 * @param isVisible Se a senha esta visivel em texto plano.
 * @param onVisibilityToggle Alterna visibilidade.
 * @param error Mensagem de erro; null se valido.
 */
@Composable
private fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    error: String? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = GeoSaudeColors.TextPrimary,
            fontWeight = FontWeight.Medium
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder, color = GeoSaudeColors.Gray400, fontSize = 14.sp)
            },
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onVisibilityToggle) {
                    Icon(
                        imageVector = if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isVisible) "Ocultar senha" else "Exibir senha",
                        tint = GeoSaudeColors.Gray400
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GeoSaudeColors.Primary,
                unfocusedBorderColor = GeoSaudeColors.InputBorder,
                focusedContainerColor = GeoSaudeColors.White,
                unfocusedContainerColor = GeoSaudeColors.White
            ),
            isError = error != null
        )

        if (error != null) {
            Text(text = error, color = GeoSaudeColors.Error, fontSize = 11.sp)
        }
    }
}

/**
 * Campo dropdown reutilizavel usando Box + DropdownMenu (compativel com KMP).
 *
 * @param label Rotulo acima do campo.
 * @param value Opcao selecionada.
 * @param options Lista de opcoes.
 * @param expanded Se o menu esta aberto.
 * @param onExpandedChange Alterna abertura do menu.
 * @param onValueChange Callback quando uma opcao e selecionada.
 * @param placeholder Texto quando nada esta selecionado.
 */
@Composable
private fun DropdownField(
    label: String,
    value: String,
    options: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    placeholder: String = "Selecione"
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = GeoSaudeColors.TextPrimary,
            fontWeight = FontWeight.Medium
        )

        Box {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                placeholder = {
                    Text(placeholder, color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { onExpandedChange(!expanded) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Abrir lista de opcoes",
                            tint = GeoSaudeColors.Gray400
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = GeoSaudeColors.InputBorder,
                    disabledContainerColor = GeoSaudeColors.White,
                    disabledTextColor = GeoSaudeColors.TextPrimary
                ),
                enabled = false
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontSize = 14.sp) },
                        onClick = { onValueChange(option) }
                    )
                }
            }
        }
    }
}

/**
 * Botao "Entrar" com indicador de carregamento.
 *
 * @param isLoading Se esta aguardando resposta da API.
 * @param onClick Callback ao clicar.
 */
@Composable
private fun LoginButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(54.dp),
        enabled = !isLoading,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GeoSaudeColors.Primary,
            disabledContainerColor = GeoSaudeColors.Primary.copy(alpha = 0.6f)
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = GeoSaudeColors.White,
                strokeWidth = 2.dp
            )
        } else {
            Text("Entrar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}