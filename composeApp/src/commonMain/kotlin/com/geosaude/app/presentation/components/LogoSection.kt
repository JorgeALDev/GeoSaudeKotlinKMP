package com.geosaude.app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.theme.GeoSaudeColors
import geosaude.composeapp.generated.resources.Res
import geosaude.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

/**
 * Logo section para uso centralizado em telas
 * Mostra logo grande + título + subtítulo (opcionais)
 */
@Composable
fun LogoSection(
    showTitle: Boolean = true,
    showSubtitle: Boolean = true,
    logoSize: Dp = 100.dp,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        // Logo
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo GeoSaúde",
            modifier = Modifier.size(logoSize),
            contentScale = ContentScale.Fit
        )

        if (showTitle || showSubtitle) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (showTitle) {
            Text(
                text = "GeoSaúde",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = GeoSaudeColors.Primary,
                textAlign = TextAlign.Center
            )
        }

        if (showSubtitle) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sistema de Combate à Endemias",
                fontSize = 16.sp,
                color = GeoSaudeColors.TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}