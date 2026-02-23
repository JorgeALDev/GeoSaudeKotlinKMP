package com.geosaude.app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.theme.GeoSaudeColors
import geosaude.composeapp.generated.resources.Res
import geosaude.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LogoHeader(
    backgroundColor: androidx.compose.ui.graphics.Color = GeoSaudeColors.White,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Logo (escudo)
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo GeoSaúde",
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Texto "GeoSaúde"
        Text(
            text = "GeoSaúde",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = GeoSaudeColors.TextPrimary
        )
    }
}