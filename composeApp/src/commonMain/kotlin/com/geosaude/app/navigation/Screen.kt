package com.geosaude.app.navigation

sealed class Screen(val route: String) {
    // Autenticação
    data object Login : Screen("login")
    data object Cadastro : Screen("cadastro")
    data object RecuperarSenha : Screen("recuperar-senha")

    // App principal
    data object Home : Screen("home")
    data object NovaVisita : Screen("nova-visita")
    data object Historico : Screen("historico")
    data object Dashboard : Screen("dashboard")
    data object Aprovacoes : Screen("aprovacoes")
    data object Usuarios : Screen("usuarios")
    data object Perfil : Screen("perfil")
    data object MinhasEstatisticas : Screen("minhas-estatisticas")
}