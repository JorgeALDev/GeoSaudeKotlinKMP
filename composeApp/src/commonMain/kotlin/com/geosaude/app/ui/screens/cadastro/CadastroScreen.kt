package com.geosaude.app.ui.screens.cadastro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.geosaude.app.ui.components.IllustrationSection
import com.geosaude.app.ui.components.LogoHeader
import com.geosaude.app.ui.theme.GeoSaudeColors
import org.koin.compose.koinInject
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

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

@Composable
private fun CadastroScreenDesktop(
    onNavigateToLogin: () -> Unit,
    onCadastroSuccess: () -> Unit
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
                        .widthIn(max = 520.dp)
                        .fillMaxHeight(0.97f)
                        .padding(horizontal = 48.dp, vertical = 20.dp)
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
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1.0f)
                    .padding(24.dp)
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
                CadastroForm(
                    onNavigateToLogin = onNavigateToLogin,
                    onCadastroSuccess = onCadastroSuccess,
                    isDesktop = false
                )
            }
        }
    }
}

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
            .padding(horizontal = if (isDesktop) 32.dp else 28.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
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
                fontSize = 15.sp,
                color = GeoSaudeColors.TextPrimary
            )

            Text(
                text = "Cadastre-se",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = GeoSaudeColors.TextPrimary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Já tem uma conta? ",
                    fontSize = 12.sp,
                    color = GeoSaudeColors.TextSecondary
                )
                Text(
                    text = "Faça login",
                    fontSize = 12.sp,
                    color = GeoSaudeColors.Primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Preencha com sua matrícula",
                    fontSize = 13.sp,
                    color = GeoSaudeColors.TextPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    lineHeight = 16.sp,
                    modifier = Modifier.height(28.dp)
                )

                OutlinedTextField(
                    value = formState.matricula,
                    onValueChange = { viewModel.onMatriculaChange(it) },
                    placeholder = {
                        Text("123456789", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().height(52.dp),
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
                        fontSize = 10.sp
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Qual sua função?",
                    fontSize = 13.sp,
                    color = GeoSaudeColors.TextPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    lineHeight = 16.sp,
                    modifier = Modifier.height(28.dp)
                )

                Box {
                    OutlinedTextField(
                        value = formState.funcao,
                        onValueChange = {},
                        placeholder = {
                            Text("-", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showFuncaoDropdown = !showFuncaoDropdown
                            }) {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = GeoSaudeColors.Gray600
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp),
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
                                text = { Text(funcao, fontSize = 14.sp) },
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Preencha com seu nome completo",
                fontSize = 13.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.nomeCompleto,
                onValueChange = { viewModel.onNomeCompletoChange(it) },
                placeholder = {
                    Text("Nome completo", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GeoSaudeColors.Primary,
                    unfocusedBorderColor = GeoSaudeColors.InputBorder,
                    focusedContainerColor = GeoSaudeColors.White,
                    unfocusedContainerColor = GeoSaudeColors.White
                ),
                isError = formState.nomeCompletoError != null
            )

            if (formState.nomeCompletoError != null) {
                Text(
                    text = formState.nomeCompletoError!!,
                    color = GeoSaudeColors.Error,
                    fontSize = 10.sp
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "E-mail",
                fontSize = 13.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = {
                    Text("E-mail", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GeoSaudeColors.Primary,
                    unfocusedBorderColor = GeoSaudeColors.InputBorder,
                    focusedContainerColor = GeoSaudeColors.White,
                    unfocusedContainerColor = GeoSaudeColors.White
                ),
                isError = formState.emailError != null
            )

            if (formState.emailError != null) {
                Text(
                    text = formState.emailError!!,
                    color = GeoSaudeColors.Error,
                    fontSize = 10.sp
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Crie uma senha",
                fontSize = 13.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.senha,
                onValueChange = { viewModel.onSenhaChange(it) },
                placeholder = {
                    Text("Senha", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                },
                visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                        Icon(
                            imageVector = if (senhaVisivel) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = GeoSaudeColors.Gray400
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
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
                    fontSize = 10.sp
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Confirme sua senha",
                fontSize = 13.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.confirmarSenha,
                onValueChange = { viewModel.onConfirmarSenhaChange(it) },
                placeholder = {
                    Text("Senha", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
                },
                visualTransformation = if (confirmarSenhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        confirmarSenhaVisivel = !confirmarSenhaVisivel
                    }) {
                        Icon(
                            imageVector = if (confirmarSenhaVisivel) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = GeoSaudeColors.Gray400
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GeoSaudeColors.Primary,
                    unfocusedBorderColor = GeoSaudeColors.InputBorder,
                    focusedContainerColor = GeoSaudeColors.White,
                    unfocusedContainerColor = GeoSaudeColors.White
                ),
                isError = formState.confirmarSenhaError != null
            )

            if (formState.confirmarSenhaError != null) {
                Text(
                    text = formState.confirmarSenhaError!!,
                    color = GeoSaudeColors.Error,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = GeoSaudeColors.Error,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                errorMessage = null
                viewModel.onCadastrar(
                    onSuccess = onCadastroSuccess,
                    onError = { errorMessage = it }
                )
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            enabled = !formState.isLoading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeoSaudeColors.Primary,
                disabledContainerColor = GeoSaudeColors.Primary.copy(alpha = 0.6f)
            )
        ) {
            if (formState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = GeoSaudeColors.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Cadastrar", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}