package com.geosaude.app.ui.screens.cadastro

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
import com.geosaude.app.ui.theme.GeoSaudeColors

@Composable
fun CadastroScreen(
    onNavigateToLogin: () -> Unit,
    onCadastroSuccess: () -> Unit
) {
    val viewModel = remember { CadastroViewModel() }
    val formState by viewModel.formState.collectAsState()

    var senhaVisivel by remember { mutableStateOf(false) }
    var confirmarSenhaVisivel by remember { mutableStateOf(false) }
    var showFuncaoDropdown by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val funcoes = listOf("Agente de Campo", "Supervisor")

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 40.dp),
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
                        text = "Cadastre-se",
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
                            text = "Já tem uma conta? ",
                            fontSize = 13.sp,
                            color = GeoSaudeColors.TextSecondary
                        )
                        Text(
                            text = "Faça login",
                            fontSize = 13.sp,
                            color = GeoSaudeColors.Primary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { onNavigateToLogin() }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Linha 1: Matrícula + Função
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Campo: Matrícula
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Preencha com sua matrícula",
                            fontSize = 14.sp,
                            color = GeoSaudeColors.TextPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.height(36.dp),
                            lineHeight = 18.sp
                        )

                        OutlinedTextField(
                            value = formState.matricula,
                            onValueChange = { viewModel.onMatriculaChange(it) },
                            placeholder = {
                                Text(
                                    "123456789",
                                    color = GeoSaudeColors.Gray400,
                                    fontSize = 14.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Campo: Função
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Qual sua função?",
                            fontSize = 14.sp,
                            color = GeoSaudeColors.TextPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.height(36.dp),
                            lineHeight = 18.sp
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
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                // Campo: Nome completo
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Preencha com seu nome completo",
                        fontSize = 14.sp,
                        color = GeoSaudeColors.TextPrimary,
                        fontWeight = FontWeight.Medium
                    )

                    OutlinedTextField(
                        value = formState.nomeCompleto,
                        onValueChange = { viewModel.onNomeCompletoChange(it) },
                        placeholder = {
                            Text("Nome completo", color = GeoSaudeColors.Gray400, fontSize = 14.sp)
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
                        isError = formState.nomeCompletoError != null
                    )

                    if (formState.nomeCompletoError != null) {
                        Text(
                            text = formState.nomeCompletoError!!,
                            color = GeoSaudeColors.Error,
                            fontSize = 12.sp
                        )
                    }
                }

                // Campo: E-mail
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "E-mail",
                        fontSize = 14.sp,
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
                        modifier = Modifier.fillMaxWidth(),
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
                        text = "Crie uma senha",
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

                // Campo: Confirmar senha
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Digite a senha novamente",
                        fontSize = 14.sp,
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
                            IconButton(onClick = { confirmarSenhaVisivel = !confirmarSenhaVisivel }) {
                                Icon(
                                    imageVector = if (confirmarSenhaVisivel) Icons.Default.VisibilityOff else Icons.Default.Visibility,
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
                        isError = formState.confirmarSenhaError != null
                    )

                    if (formState.confirmarSenhaError != null) {
                        Text(
                            text = formState.confirmarSenhaError!!,
                            color = GeoSaudeColors.Error,
                            fontSize = 12.sp
                        )
                    }
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

                // Botão Cadastrar
                Button(
                    onClick = {
                        errorMessage = null
                        viewModel.onCadastrar(
                            onSuccess = onCadastroSuccess,
                            onError = { errorMessage = it }
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
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
                        Text("Cadastrar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}