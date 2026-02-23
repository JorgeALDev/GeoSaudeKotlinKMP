package com.geosaude.app.presentation.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Cadastro : Screen("cadastro")
    data object Main : Screen("main")
    data object NovaVisita : Screen("nova_visita")
    data object Dashboard : Screen("dashboard")
    data object Visitas : Screen("visitas")
    data object Historico : Screen("historico")
    data object Relatorios : Screen("relatorios")
}