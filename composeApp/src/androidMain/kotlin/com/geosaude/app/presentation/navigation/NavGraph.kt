package com.geosaude.app.presentation.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geosaude.app.presentation.screens.login.LoginScreen
import com.geosaude.app.presentation.screens.cadastro.CadastroScreen
import com.geosaude.app.presentation.screens.main.MainScreen

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
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
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

        // Tela Principal (com Bottom Nav)
        composable(Screen.Main.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // TODO: Adicionar outras telas (Main, NovaVisita, etc)
    }
}