package com.geosaude.app

import androidx.compose.runtime.*
import com.geosaude.app.presentation.screens.login.LoginScreen
import com.geosaude.app.presentation.screens.cadastro.CadastroScreen
import com.geosaude.app.presentation.theme.GeoSaudeTheme

@Composable
actual fun App() {
    GeoSaudeTheme {
        var currentScreen by remember { mutableStateOf("login") }

        when (currentScreen) {
            "login" -> LoginScreen(
                onNavigateToCadastro = { currentScreen = "cadastro" },
                onNavigateToRecuperarSenha = { /* TODO */ },
                onLoginSuccess = { /* TODO */ }
            )
            "cadastro" -> CadastroScreen(
                onNavigateToLogin = { currentScreen = "login" },
                onCadastroSuccess = { currentScreen = "login" }
            )
        }
    }
}