package com.geosaude.app.ui.screens.login

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
import com.geosaude.app.ui.components.IllustrationSection
import com.geosaude.app.ui.components.LogoHeader
import com.geosaude.app.ui.theme.GeoSaudeColors
import org.koin.compose.koinInject

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
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.Background),
            contentAlignment = Alignment.Center
        ) {
            IllustrationSection()
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GeoSaudeColors.White)
        ) {
            LogoHeader(backgroundColor = GeoSaudeColors.White)

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .widthIn(max = 480.dp)
                        .wrapContentHeight()
                        .padding(48.dp)
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
                    LoginForm(
                        onNavigateToCadastro = onNavigateToCadastro,
                        onNavigateToRecuperarSenha = onNavigateToRecuperarSenha,
                        onLoginSuccess = onLoginSuccess,
                        isDesktop = true
                    )
                }
            }
        }
    }
}

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
}

@Composable
private fun LoginForm(
    onNavigateToCadastro: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    isDesktop: Boolean
) {
    val viewModel = org.koin.compose.koinInject<LoginViewModel>()
    val formState by viewModel.formState.collectAsState()

    var senhaVisivel by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = if (isDesktop) 64.dp else 28.dp,
                vertical = if (isDesktop) 48.dp else 40.dp
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Coloque sua matrícula",
                fontSize = 14.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.matricula,
                onValueChange = { viewModel.onMatriculaChange(it) },
                placeholder = {
                    Text(
                        "Matrícula",
                        color = GeoSaudeColors.Gray400,
                        fontSize = 14.sp
                    )
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