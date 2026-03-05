package com.geosaude.app.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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

/**
 * Tela de login com layout responsivo.
 * Breakpoint de 800dp para alternar entre Mobile e Desktop.
 * Conteudo dos campos e IDENTICO em ambos, apenas o design (layout) muda.
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
// Layout Desktop
// ---------------------------------------------------------------------------

@Composable
private fun LoginScreenDesktop(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.Background)
        ) {
            IllustrationSection()
            LogoHeader(
                backgroundColor = GeoSaudeColors.Background,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.White),
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
                colors = CardDefaults.cardColors(containerColor = GeoSaudeColors.CardBackground)
            ) {
                LoginForm(
                    onNavigateToCadastro = onNavigateToCadastro,
                    onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                    onLoginSuccess = onLoginSuccess,
                    horizontalPadding = 48.dp,
                    verticalPadding = 40.dp,
                    spacing = 20.dp
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Mobile
// ---------------------------------------------------------------------------

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
                colors = CardDefaults.cardColors(containerColor = GeoSaudeColors.CardBackground)
            ) {
                LoginForm(
                    onNavigateToCadastro = onNavigateToCadastro,
                    onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                    onLoginSuccess = onLoginSuccess,
                    horizontalPadding = 24.dp,
                    verticalPadding = 28.dp,
                    spacing = 16.dp
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Formulario UNICO (usado por Mobile e Desktop)
// Campos identicos: matricula + senha + Entrar + Esqueceu senha?
// Sem campo "funcao" — a funcao vem do backend pela matricula.
// ---------------------------------------------------------------------------

@Composable
private fun LoginForm(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    horizontalPadding: androidx.compose.ui.unit.Dp,
    verticalPadding: androidx.compose.ui.unit.Dp,
    spacing: androidx.compose.ui.unit.Dp
) {
    val viewModel = org.koin.compose.koinInject<LoginViewModel>()
    val formState by viewModel.formState.collectAsState()

    var senhaVisivel by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        // Cabecalho
        LoginFormHeader(onNavigateToCadastro = onNavigateToCadastro)

        // Campo matricula
        LoginTextField(
            label = "Coloque sua matricula",
            value = formState.matricula,
            onValueChange = { viewModel.onMatriculaChange(it) },
            placeholder = "Matricula",
            error = formState.matriculaError
        )

        // Campo senha
        LoginPasswordField(
            label = "Coloque sua senha",
            value = formState.senha,
            onValueChange = { viewModel.onSenhaChange(it) },
            placeholder = "Senha",
            isVisible = senhaVisivel,
            onVisibilityToggle = { senhaVisivel = !senhaVisivel },
            error = formState.senhaError
        )

        // Erro geral
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = GeoSaudeColors.Error, fontSize = 13.sp)
        }

        // Botao Entrar
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

        // "Esqueceu sua senha?"
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
// Componentes reutilizaveis
// ---------------------------------------------------------------------------

@Composable
private fun LoginFormHeader(onNavigateToCadastro: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Bem vindo ao ")
                    withStyle(SpanStyle(color = GeoSaudeColors.Primary, fontWeight = FontWeight.SemiBold)) {
                        append("GeoSaude")
                    }
                },
                fontSize = 15.sp,
                color = GeoSaudeColors.TextPrimary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Nao tem conta? ", fontSize = 11.sp, color = GeoSaudeColors.TextSecondary)
                Text(
                    "Cadastre-se",
                    fontSize = 11.sp,
                    color = GeoSaudeColors.Primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToCadastro() }
                )
            }
        }

        Text("Login", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = GeoSaudeColors.TextPrimary)
    }
}

@Composable
private fun LoginTextField(
    label: String, value: String, onValueChange: (String) -> Unit,
    placeholder: String, error: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, fontSize = 14.sp, color = GeoSaudeColors.TextPrimary, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value, onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = GeoSaudeColors.Gray400, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(54.dp), singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GeoSaudeColors.Primary, unfocusedBorderColor = GeoSaudeColors.InputBorder,
                focusedContainerColor = GeoSaudeColors.White, unfocusedContainerColor = GeoSaudeColors.White
            ),
            isError = error != null
        )
        if (error != null) Text(error, color = GeoSaudeColors.Error, fontSize = 11.sp)
    }
}

@Composable
private fun LoginPasswordField(
    label: String, value: String, onValueChange: (String) -> Unit,
    placeholder: String, isVisible: Boolean, onVisibilityToggle: () -> Unit,
    error: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, fontSize = 14.sp, color = GeoSaudeColors.TextPrimary, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value, onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = GeoSaudeColors.Gray400, fontSize = 14.sp) },
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onVisibilityToggle) {
                    Icon(
                        if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isVisible) "Ocultar senha" else "Exibir senha",
                        tint = GeoSaudeColors.Gray400
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp), singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GeoSaudeColors.Primary, unfocusedBorderColor = GeoSaudeColors.InputBorder,
                focusedContainerColor = GeoSaudeColors.White, unfocusedContainerColor = GeoSaudeColors.White
            ),
            isError = error != null
        )
        if (error != null) Text(error, color = GeoSaudeColors.Error, fontSize = 11.sp)
    }
}

@Composable
private fun LoginButton(isLoading: Boolean, onClick: () -> Unit) {
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
            CircularProgressIndicator(modifier = Modifier.size(22.dp), color = GeoSaudeColors.White, strokeWidth = 2.dp)
        } else {
            Text("Entrar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}