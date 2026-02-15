package com.geosaude.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.ui.theme.GeoSaudeColors

@Composable
fun IllustrationSection() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(GeoSaudeColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(48.dp)
        ) {
            // TODO: Adicionar ilustração real (mapa + mosquito + pin)
            // Por enquanto, placeholder
            Text(
                text = "🗺️",
                fontSize = 120.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "🦟",
                fontSize = 80.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "GeoSaúde",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = GeoSaudeColors.Primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sistema de Combate à Dengue",
                fontSize = 16.sp,
                color = GeoSaudeColors.TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}