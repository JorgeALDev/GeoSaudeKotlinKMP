package com.geosaude.app.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.geosaude.app.presentation.components.BottomNavigationBar
import com.geosaude.app.presentation.screens.novavisita.NovaVisitaScreen

@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    var currentRoute by remember { mutableStateOf("nova_visita") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = currentRoute,
                onNavigate = { route ->
                    currentRoute = route
                },
                onLogout = onLogout
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (currentRoute) {
                "dashboard" -> {
                    // TODO: DashboardScreen()
                }
                "visitas" -> {
                    // TODO: VisitasScreen()
                }
                "nova_visita" -> {
                    NovaVisitaScreen()
                }
                "historico" -> {
                    // TODO: HistoricoScreen()
                }
            }
        }
    }
}