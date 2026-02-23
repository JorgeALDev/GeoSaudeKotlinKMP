package com.geosaude.app

import androidx.compose.runtime.Composable
import com.geosaude.app.presentation.navigation.NavGraph
import com.geosaude.app.presentation.theme.GeoSaudeTheme

@Composable
actual fun App() {
    GeoSaudeTheme {
        NavGraph()
    }
}