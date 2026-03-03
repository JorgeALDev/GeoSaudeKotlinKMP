package com.geosaude.app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.geosaude.app.presentation.theme.GeoSaudeColors
import geosaude.composeapp.generated.resources.Res
import geosaude.composeapp.generated.resources.illustration
import org.jetbrains.compose.resources.painterResource

/**
 * Secao de ilustracao exibida na metade esquerda das telas de Login e Cadastro (Desktop).
 * Conforme o Figma, exibe APENAS a imagem centralizada sem nenhum texto sobreposto.
 * A imagem preenche a area disponivel mantendo proporcao, centralizada verticalmente.
 */
@Composable
fun IllustrationSection() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GeoSaudeColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.illustration),
            contentDescription = "Ilustracao de agente de saude em campo com mapa do Brasil",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(32.dp),
            contentScale = ContentScale.Fit
        )
    }
}