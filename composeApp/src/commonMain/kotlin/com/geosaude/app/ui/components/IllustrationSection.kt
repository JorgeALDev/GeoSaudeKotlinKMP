package com.geosaude.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.ui.theme.GeoSaudeColors
import geosaude.composeapp.generated.resources.Res
import geosaude.composeapp.generated.resources.illustration
import org.jetbrains.compose.resources.painterResource

@Composable
fun IllustrationSection() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GeoSaudeColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp)
        ) {
            // Ilustração
            Image(
                painter = painterResource(Res.drawable.illustration),
                contentDescription = "Ilustração GeoSaúde",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Título
            Text(
                text = "GeoSaúde",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = GeoSaudeColors.Primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtítulo
            Text(
                text = "Sistema de Combate à Endemias",
                fontSize = 18.sp,
                color = GeoSaudeColors.TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}