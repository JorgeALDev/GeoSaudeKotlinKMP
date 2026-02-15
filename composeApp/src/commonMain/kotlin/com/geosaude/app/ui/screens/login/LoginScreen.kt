package com.geosaude.app.ui.screens.login

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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.ui.components.IllustrationSection
import com.geosaude.app.ui.theme.GeoSaudeColors

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

@Composable
private fun LoginScreenDesktop(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Lado esquerdo: Ilustração
        Box(modifier = Modifier.weight(1f)) {
            IllustrationSection()
        }

        // Lado direito: Formulário
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.White),
            contentAlignment = Alignment.Center
        ) {
            LoginForm(
                onNavigateToCadastro = onNavigateToCadastro,
                onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                onLoginSuccess = onLoginSuccess,
                isDesktop = true
            )
        }
    }
}

@Composable
private fun LoginScreenMobile(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GeoSaudeColors.Background)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .widthIn(max = 400.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = GeoSaudeColors.Primary.copy(alpha = 0.1f)
                ),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = GeoSaudeColors.CardBackground
            )
        ) {
            LoginForm(
                onNavigateToCadastro = onNavigateToCadastro,
                onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                onLoginSuccess = onLoginSuccess,
                isDesktop = false
            )
        }
    }
}

@Composable
private fun LoginForm(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    isDesktop: Boolean
) {
    val viewModel = remember { LoginViewModel() }
    val formState by viewModel.formState.collectAsState()

    var senhaVisivel by remember { mutableStateOf(false) }
    var funcaoSelecionada by remember { mutableStateOf("") }
    var showFuncaoDropdown by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val funcoes = listOf("Agente de Campo", "Supervisor")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isDesktop) {
                    Modifier.padding(horizontal = 64.dp, vertical = 48.dp)
                } else {
                    Modifier.padding(horizontal = 28.dp, vertical = 40.dp)
                }
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Bem vindo ao ")
                    withStyle(SpanStyle(color = GeoSaudeColors.Primary, fontWeight = FontWeight.SemiBold)) {
                        append("GeoSaúde")
                    }
                },
                fontSize = 16.sp,
                color = GeoSaudeColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = GeoSaudeColors.TextPrimary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Não tem conta? ",
                    fontSize = 13.sp,
                    color = GeoSaudeColors.TextSecondary
                )
                Text(
                    text = "Cadastre-se",
                    fontSize = 13.sp,
                    color = GeoSaudeColors.Primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToCadastro() }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo: E-mail ou matrícula
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Coloque seu e-mail ou matrícula",
                fontSize = 14.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.matricula,
                onValueChange = { viewModel.onMatriculaChange(it) },
                placeholder = {
                    Text("E-mail ou matrícula", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GeoSaudeColors.Primary,
                    unfocusedBorderColor = GeoSaudeColors.InputBorder,
                    focusedContainerColor = GeoSaudeColors.White,
                    unfocusedContainerColor = GeoSaudeColors.White
                ),
                isError = formState.matriculaError != null
            )

            if (formState.matriculaError != null) {
                Text(
                    text = formState.matriculaError!!,
                    color = GeoSaudeColors.Error,
                    fontSize = 12.sp
                )
            }
        }

        // Campo: Senha
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Coloque sua senha",
                fontSize = 14.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.senha,
                onValueChange = { viewModel.onSenhaChange(it) },
                placeholder = {
                    Text("Senha", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                },
                visualTransformation = if (senhaVisivel) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                        Icon(
                            imageVector = if (senhaVisivel) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = null,
                            tint = GeoSaudeColors.Gray400
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GeoSaudeColors.Primary,
                    unfocusedBorderColor = GeoSaudeColors.InputBorder,
                    focusedContainerColor = GeoSaudeColors.White,
                    unfocusedContainerColor = GeoSaudeColors.White
                ),
                isError = formState.senhaError != null
            )

            if (formState.senhaError != null) {
                Text(
                    text = formState.senhaError!!,
                    color = GeoSaudeColors.Error,
                    fontSize = 12.sp
                )
            }
        }

        // Campo: Qual sua função?
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Qual sua função?",
                fontSize = 14.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            Box {
                OutlinedTextField(
                    value = funcaoSelecionada,
                    onValueChange = {},
                    placeholder = {
                        Text("-", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showFuncaoDropdown = !showFuncaoDropdown }) {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = GeoSaudeColors.Gray600
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = GeoSaudeColors.InputBorder,
                        disabledContainerColor = GeoSaudeColors.White,
                        disabledTextColor = GeoSaudeColors.TextPrimary
                    ),
                    enabled = false
                )

                DropdownMenu(
                    expanded = showFuncaoDropdown,
                    onDismissRequest = { showFuncaoDropdown = false },
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    funcoes.forEach { funcao ->
                        DropdownMenuItem(
                            text = { Text(funcao, fontSize = 14.sp) },
                            onClick = {
                                funcaoSelecionada = funcao
                                showFuncaoDropdown = false
                            }
                        )
                    }
                }
            }
        }

        // Link "Esqueceu sua senha?"
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

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = GeoSaudeColors.Error,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Botão Entrar
        Button(
            onClick = {
                errorMessage = null
                viewModel.onLogin(
                    onSuccess = onLoginSuccess,
                    onError = { errorMessage = it }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !formState.isLoading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeoSaudeColors.Primary,
                disabledContainerColor = GeoSaudeColors.Primary.copy(alpha = 0.6f)
            )
        ) {
            if (formState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = GeoSaudeColors.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Entrar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}