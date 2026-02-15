package com.geosaude.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geosaude.app.ui.screens.login.LoginScreen
import com.geosaude.app.ui.screens.cadastro.CadastroScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToCadastro = {
                    navController.navigate(Screen.Cadastro.route)
                },
                onNavigateToRecuperarSenha = {
                    navController.navigate(Screen.RecuperarSenha.route)
                },
                onLoginSuccess = { role ->
                    val destination = if (role == "supervisor") {
                        Screen.Dashboard.route
                    } else {
                        Screen.Home.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

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

        // Outras telas serão adicionadas depois
    }
}