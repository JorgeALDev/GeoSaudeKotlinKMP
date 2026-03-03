package com.geosaude.app.presentation.screens.cadastro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.components.IllustrationSection
import com.geosaude.app.presentation.components.LogoHeader
import com.geosaude.app.presentation.theme.GeoSaudeColors

// ---------------------------------------------------------------------------
// Ponto de entrada: layout responsivo Mobile/Desktop
// ---------------------------------------------------------------------------

/**
 * Tela de cadastro com layout responsivo.
 * Breakpoint de 800dp para alternar entre Mobile e Desktop.
 *
 * @param onNavigateToLogin Callback para voltar a tela de login.
 * @param onCadastroSuccess Callback executado apos cadastro bem-sucedido.
 */
@Composable
fun CadastroScreen(
    onNavigateToLogin: () -> Unit,
    onCadastroSuccess: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth >= 800.dp

        if (isDesktop) {
            CadastroScreenDesktop(
                onNavigateToLogin = onNavigateToLogin,
                onCadastroSuccess = onCadastroSuccess
            )
        } else {
            CadastroScreenMobile(
                onNavigateToLogin = onNavigateToLogin,
                onCadastroSuccess = onCadastroSuccess
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Desktop (Frame 15 do Figma)
// ---------------------------------------------------------------------------

/**
 * Layout desktop conforme Frame 15 do Figma:
 * - Esquerda: fundo verde com logo no topo + ilustracao centralizada (sem textos).
 * - Direita: fundo branco com formulario de cadastro em card.
 */
@Composable
private fun CadastroScreenDesktop(
    onNavigateToLogin: () -> Unit,
    onCadastroSuccess: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Metade esquerda: ilustracao sem textos sobrepostos
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.Background)
        ) {
            IllustrationSection()

            // Logo "GeoSaude" no topo esquerdo sobre a ilustracao
            LogoHeader(
                backgroundColor = GeoSaudeColors.Background,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }

        // Metade direita: formulario de cadastro
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
                        .widthIn(max = 520.dp)
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .padding(vertical = 24.dp)
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
                    CadastroForm(
                        onNavigateToLogin = onNavigateToLogin,
                        onCadastroSuccess = onCadastroSuccess,
                        isDesktop = true
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Mobile (Figma Cadastro Mobile)
// ---------------------------------------------------------------------------

/**
 * Layout mobile conforme Figma:
 * - Logo "GeoSaude" no topo.
 * - Card arredondado com formulario completo.
 */
@Composable
private fun CadastroScreenMobile(
    onNavigateToLogin: () -> Unit,
    onCadastroSuccess: () -> Unit
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
                    .padding(horizontal = 20.dp, vertical = 8.dp)
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
                CadastroForm(
                    onNavigateToLogin = onNavigateToLogin,
                    onCadastroSuccess = onCadastroSuccess,
                    isDesktop = false
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Formulario de cadastro (compartilhado Mobile/Desktop)
// ---------------------------------------------------------------------------

/**
 * Formulario de cadastro conforme Figma Frame 15 e Mobile.
 * Campos na ordem do Figma:
 * 1. Matricula + Funcao (lado a lado)
 * 2. Nome completo
 * 3. E-mail
 * 4. Crie uma senha
 * 5. Digite a senha novamente
 * 6. Botao "Cadastrar"
 *
 * @param onNavigateToLogin Callback para navegar ao login.
 * @param onCadastroSuccess Callback apos cadastro bem-sucedido.
 * @param isDesktop Indica layout desktop (ajusta padding).
 */
@Composable
private fun CadastroForm(
    onNavigateToLogin: () -> Unit,
    onCadastroSuccess: () -> Unit,
    isDesktop: Boolean
) {
    val viewModel = org.koin.compose.koinInject<CadastroViewModel>()
    val formState by viewModel.formState.collectAsState()

    var senhaVisivel by remember { mutableStateOf(false) }
    var confirmarSenhaVisivel by remember { mutableStateOf(false) }
    var showFuncaoDropdown by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val funcoes = listOf("Agente de Campo", "Supervisor")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = if (isDesktop) 32.dp else 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Cabecalho: "Bem vindo ao GeoSaude" + "Cadastre-se" + link login
        CadastroFormHeader(onNavigateToLogin = onNavigateToLogin)

        // Matricula e Funcao lado a lado conforme Figma
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Campo matricula (somente digitos, max 8)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Preencha com sua matricula",
                    fontSize = 12.sp,
                    color = GeoSaudeColors.TextPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    lineHeight = 15.sp
                )

                OutlinedTextField(
                    value = formState.matricula,
                    onValueChange = { viewModel.onMatriculaChange(it) },
                    placeholder = {
                        Text("123456789", color = GeoSaudeColors.Gray400, fontSize = 13.sp)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = outlinedFieldColors(),
                    isError = formState.matriculaError != null
                )

                if (formState.matriculaError != null) {
                    Text(
                        text = formState.matriculaError!!,
                        color = GeoSaudeColors.Error,
                        fontSize = 10.sp
                    )
                }
            }

            // Campo funcao (dropdown compativel KMP)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Qual sua funcao?",
                    fontSize = 12.sp,
                    color = GeoSaudeColors.TextPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    lineHeight = 15.sp
                )

                Box {
                    OutlinedTextField(
                        value = formState.funcao,
                        onValueChange = {},
                        placeholder = {
                            Text("-", color = GeoSaudeColors.Gray400, fontSize = 13.sp)
                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showFuncaoDropdown = !showFuncaoDropdown }) {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Abrir lista de funcoes",
                                    tint = GeoSaudeColors.Gray600
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = GeoSaudeColors.InputBorder,
                            disabledContainerColor = GeoSaudeColors.White,
                            disabledTextColor = GeoSaudeColors.TextPrimary
                        ),
                        enabled = false,
                        isError = formState.funcaoError != null
                    )

                    DropdownMenu(
                        expanded = showFuncaoDropdown,
                        onDismissRequest = { showFuncaoDropdown = false }
                    ) {
                        funcoes.forEach { funcao ->
                            DropdownMenuItem(
                                text = { Text(funcao, fontSize = 13.sp) },
                                onClick = {
                                    viewModel.onFuncaoChange(funcao)
                                    showFuncaoDropdown = false
                                }
                            )
                        }
                    }
                }

                if (formState.funcaoError != null) {
                    Text(
                        text = formState.funcaoError!!,
                        color = GeoSaudeColors.Error,
                        fontSize = 10.sp
                    )
                }
            }
        }

        // Campo nome completo
        CadastroTextField(
            label = "Preencha com seu nome completo",
            value = formState.nomeCompleto,
            onValueChange = { viewModel.onNomeCompletoChange(it) },
            placeholder = "Nome completo",
            error = formState.nomeCompletoError
        )

        // Campo e-mail
        CadastroTextField(
            label = "E-mail",
            value = formState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            placeholder = "E-mail",
            keyboardType = KeyboardType.Email,
            error = formState.emailError
        )

        // Campo senha
        CadastroPasswordField(
            label = "Crie uma senha",
            value = formState.senha,
            onValueChange = { viewModel.onSenhaChange(it) },
            placeholder = "Senha",
            isVisible = senhaVisivel,
            onVisibilityToggle = { senhaVisivel = !senhaVisivel },
            error = formState.senhaError
        )

        // Campo confirmar senha - label conforme Figma: "Digite a senha novamente"
        CadastroPasswordField(
            label = "Digite a senha novamente",
            value = formState.confirmarSenha,
            onValueChange = { viewModel.onConfirmarSenhaChange(it) },
            placeholder = "Senha",
            isVisible = confirmarSenhaVisivel,
            onVisibilityToggle = { confirmarSenhaVisivel = !confirmarSenhaVisivel },
            error = formState.confirmarSenhaError
        )

        // Erro geral de API
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = GeoSaudeColors.Error,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Botao "Cadastrar"
        Button(
            onClick = {
                errorMessage = null
                viewModel.onCadastrar(
                    onSuccess = onCadastroSuccess,
                    onError = { errorMessage = it }
                )
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = !formState.isLoading,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeoSaudeColors.Primary,
                disabledContainerColor = GeoSaudeColors.Primary.copy(alpha = 0.6f)
            )
        ) {
            if (formState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = GeoSaudeColors.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Cadastrar", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Componentes internos do cadastro
// ---------------------------------------------------------------------------

/**
 * Cabecalho do formulario: "Bem vindo ao GeoSaude" + "Cadastre-se"
 * + "Ja tem uma conta? Faca login" alinhado a direita.
 */
@Composable
private fun CadastroFormHeader(onNavigateToLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Linha com saudacao e link de login
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
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
                fontSize = 14.sp,
                color = GeoSaudeColors.TextPrimary
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Ja tem uma conta?",
                    fontSize = 11.sp,
                    color = GeoSaudeColors.TextSecondary
                )
                Text(
                    text = "Faca login",
                    fontSize = 11.sp,
                    color = GeoSaudeColors.Primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }

        Text(
            text = "Cadastre-se",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = GeoSaudeColors.TextPrimary
        )
    }
}

/**
 * Campo de texto reutilizavel para o formulario de cadastro.
 */
@Composable
private fun CadastroTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    error: String? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = GeoSaudeColors.TextPrimary,
            fontWeight = FontWeight.Medium
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder, color = GeoSaudeColors.Gray400, fontSize = 13.sp)
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth().height(50.dp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = outlinedFieldColors(),
            isError = error != null
        )

        if (error != null) {
            Text(text = error, color = GeoSaudeColors.Error, fontSize = 10.sp)
        }
    }
}

/**
 * Campo de senha reutilizavel para o formulario de cadastro.
 */
@Composable
private fun CadastroPasswordField(
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
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = GeoSaudeColors.TextPrimary,
            fontWeight = FontWeight.Medium
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder, color = GeoSaudeColors.Gray400, fontSize = 13.sp)
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
            modifier = Modifier.fillMaxWidth().height(50.dp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = outlinedFieldColors(),
            isError = error != null
        )

        if (error != null) {
            Text(text = error, color = GeoSaudeColors.Error, fontSize = 10.sp)
        }
    }
}

/**
 * Cores padrao dos OutlinedTextField do formulario de cadastro.
 * Centraliza para evitar repeticao em cada campo.
 */
@Composable
private fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = GeoSaudeColors.Primary,
    unfocusedBorderColor = GeoSaudeColors.InputBorder,
    focusedContainerColor = GeoSaudeColors.White,
    unfocusedContainerColor = GeoSaudeColors.White
)