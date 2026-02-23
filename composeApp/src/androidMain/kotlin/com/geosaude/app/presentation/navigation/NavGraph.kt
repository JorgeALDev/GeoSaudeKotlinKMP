package com.geosaude.app.presentation.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geosaude.app.presentation.screens.login.LoginScreen
import com.geosaude.app.presentation.screens.cadastro.CadastroScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Tela de Login
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToCadastro = {
                    navController.navigate(Screen.Cadastro.route)
                },
                onNavigateToRecuperarSenha = {
                    // TODO: Implementar recuperação de senha
                },
                onLoginSuccess = { funcao ->
                    // TODO: Navegar para tela principal
                    // navController.navigate(Screen.Main.route)
                }
            )
        }

        // Tela de Cadastro
        composable(Screen.Cadastro.route) {
            CadastroScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onCadastroSuccess = {
                    navController.popBackStack()
                }
            )
        }

        // TODO: Adicionar outras telas (Main, NovaVisita, etc)
    }
}