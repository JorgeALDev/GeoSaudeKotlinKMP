package com.geosaude.app

import androidx.compose.runtime.Composable
import com.geosaude.app.navigation.NavGraph
import com.geosaude.app.ui.theme.GeoSaudeTheme

@Composable
fun App() {
    GeoSaudeTheme {
        NavGraph()
    }
}