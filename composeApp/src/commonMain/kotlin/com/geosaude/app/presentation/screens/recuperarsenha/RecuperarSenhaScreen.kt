package com.geosaude.app.presentation.screens.recuperarsenha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
 * Tela de recuperação de senha com layout responsivo.
 * Breakpoint de 800dp para alternar entre Mobile e Desktop.
 *
 * @param onNavigateBack Callback para voltar à tela anterior (ex: login).
 * @param onEnviarSuccess Callback executado após envio bem-sucedido do e-mail.
 */
@Composable
fun RecuperarSenhaScreen(
    onNavigateBack: () -> Unit,
    onEnviarSuccess: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth >= 800.dp

        if (isDesktop) {
            RecuperarSenhaScreenDesktop(
                onNavigateBack = onNavigateBack,
                onEnviarSuccess = onEnviarSuccess
            )
        } else {
            RecuperarSenhaScreenMobile(
                onNavigateBack = onNavigateBack,
                onEnviarSuccess = onEnviarSuccess
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Desktop
// ---------------------------------------------------------------------------

/**
 * Layout desktop:
 * - Esquerda: fundo verde com logo no topo + ilustração centralizada.
 * - Direita: fundo branco com formulário de recuperação em card.
 */
@Composable
private fun RecuperarSenhaScreenDesktop(
    onNavigateBack: () -> Unit,
    onEnviarSuccess: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Metade esquerda: ilustração
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

        // Metade direita: formulário de recuperação
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
                    RecuperarSenhaForm(
                        onEnviarSuccess = onEnviarSuccess,
                        isDesktop = true
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Mobile
// ---------------------------------------------------------------------------

/**
 * Layout mobile:
 * - Logo "GeoSaude" no topo.
 * - Card arredondado com formulário de recuperação.
 */
@Composable
private fun RecuperarSenhaScreenMobile(
    onNavigateBack: () -> Unit,
    onEnviarSuccess: () -> Unit
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
                RecuperarSenhaForm(
                    onEnviarSuccess = onEnviarSuccess,
                    isDesktop = false
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Formulário de recuperação de senha (compartilhado Mobile/Desktop)
// ---------------------------------------------------------------------------

/**
 * Formulário de recuperação de senha conforme o design:
 * 1. Cabeçalho: "Bem vindo ao GeoSaude" + "Recuperação de senha"
 * 2. Campo: "Informe sua matrícula ou e-mail"
 * 3. Texto de instrução
 * 4. Botão "Enviar"
 *
 * @param onEnviarSuccess Callback após envio bem-sucedido.
 * @param isDesktop Indica layout desktop (ajusta padding).
 */
@Composable
private fun RecuperarSenhaForm(
    onEnviarSuccess: () -> Unit,
    isDesktop: Boolean
) {
    val viewModel = org.koin.compose.koinInject<RecuperarSenhaViewModel>()
    val formState by viewModel.formState.collectAsState()

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = if (isDesktop) 32.dp else 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Cabeçalho
        RecuperacaoSenhaFormHeader()

        // Campo: matrícula ou e-mail
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Informe sua matrícula ou e-mail",
                fontSize = 12.sp,
                color = GeoSaudeColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = formState.matriculaOuEmail,
                onValueChange = { viewModel.onMatriculaOuEmailChange(it) },
                placeholder = {
                    Text(
                        text = "Matrícula ou e-mail",
                        color = GeoSaudeColors.Gray400,
                        fontSize = 13.sp
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GeoSaudeColors.Primary,
                    unfocusedBorderColor = GeoSaudeColors.InputBorder,
                    focusedContainerColor = GeoSaudeColors.White,
                    unfocusedContainerColor = GeoSaudeColors.White
                ),
                isError = formState.matriculaOuEmailError != null
            )

            // Texto de instrução abaixo do campo (conforme design)
            Text(
                text = "Um e-mail vai ser enviado para seu e-mail onde você vai poder criar uma senha nova",
                fontSize = 11.sp,
                color = GeoSaudeColors.TextSecondary,
                lineHeight = 15.sp
            )

            if (formState.matriculaOuEmailError != null) {
                Text(
                    text = formState.matriculaOuEmailError!!,
                    color = GeoSaudeColors.Error,
                    fontSize = 10.sp
                )
            }
        }

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

        // Botão "Enviar"
        Button(
            onClick = {
                errorMessage = null
                viewModel.onEnviar(
                    onSuccess = onEnviarSuccess,
                    onError = { errorMessage = it }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
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
                Text("Enviar", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Cabeçalho do formulário
// ---------------------------------------------------------------------------

/**
 * Cabeçalho: "Bem vindo ao GeoSaude" + "Recuperação de senha"
 */
@Composable
private fun RecuperacaoSenhaFormHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
                    append("GeoSaude")
                }
            },
            fontSize = 14.sp,
            color = GeoSaudeColors.TextPrimary
        )

        Text(
            text = "Recuperação de senha",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = GeoSaudeColors.TextPrimary
        )
    }
}