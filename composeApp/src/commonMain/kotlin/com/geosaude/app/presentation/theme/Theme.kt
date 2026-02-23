package com.geosaude.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val GeoSaudeColorScheme = lightColorScheme(
    primary = GeoSaudeColors.Primary,
    onPrimary = GeoSaudeColors.White,
    primaryContainer = GeoSaudeColors.Background,
    onPrimaryContainer = GeoSaudeColors.PrimaryDark,

    secondary = GeoSaudeColors.PrimaryDark,
    onSecondary = GeoSaudeColors.White,

    background = GeoSaudeColors.Background,
    onBackground = GeoSaudeColors.TextPrimary,

    surface = GeoSaudeColors.White,
    onSurface = GeoSaudeColors.TextPrimary,

    error = GeoSaudeColors.Error,
    onError = GeoSaudeColors.White,

    outline = GeoSaudeColors.InputBorder
)

@Composable
fun GeoSaudeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GeoSaudeColorScheme,
        content = content
    )
}