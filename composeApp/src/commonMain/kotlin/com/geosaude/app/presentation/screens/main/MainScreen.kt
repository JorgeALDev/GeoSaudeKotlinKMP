package com.geosaude.app.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.components.BottomNavigationBar
import com.geosaude.app.presentation.screens.novavisita.NovaVisitaScreen
import com.geosaude.app.presentation.theme.GeoSaudeColors
import geosaude.composeapp.generated.resources.Res
import geosaude.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

private val SidebarGreen = Color(0xFF2D5F3E)

// ---------------------------------------------------------------------------
// Ponto de entrada: detecta largura e usa BottomNav (mobile) ou Sidebar (desktop)
// ---------------------------------------------------------------------------

/**
 * Tela principal do app apos login.
 * Gerencia a navegacao entre as telas internas (Painel, Nova visita, etc).
 *
 * MOBILE: usa BottomNavigationBar existente (componente compartilhado).
 * DESKTOP: usa Sidebar fixa na lateral esquerda.
 *
 * Nunca mostra ambos ao mesmo tempo.
 *
 * @param onLogout Callback para voltar a tela de login.
 */
@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    var currentRoute by remember { mutableStateOf("nova_visita") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth >= 800.dp

        if (isDesktop) {
            MainScreenDesktop(
                currentRoute = currentRoute,
                onNavigate = { currentRoute = it },
                onLogout = onLogout
            )
        } else {
            MainScreenMobile(
                currentRoute = currentRoute,
                onNavigate = { currentRoute = it },
                onLogout = onLogout
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Mobile: conteudo + BottomNavigationBar existente
// ---------------------------------------------------------------------------

/**
 * Layout mobile: conteudo da tela ativa + BottomNavigationBar no rodape.
 * Usa o componente BottomNavigationBar ja existente no projeto.
 */
@Composable
private fun MainScreenMobile(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = currentRoute,
                onNavigate = onNavigate,
                onLogout = onLogout
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            MainContent(currentRoute = currentRoute)
        }
    }
}

// ---------------------------------------------------------------------------
// Layout Desktop: Sidebar fixa + conteudo
// ---------------------------------------------------------------------------

/**
 * Layout desktop: Sidebar fixa na lateral esquerda + conteudo da tela ativa.
 * Sem BottomNavigationBar.
 */
@Composable
private fun MainScreenDesktop(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Sidebar: navegacao lateral
        DesktopSidebar(
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            onLogout = onLogout
        )

        // Area principal de conteudo
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            MainContent(currentRoute = currentRoute)
        }
    }
}

// ---------------------------------------------------------------------------
// Conteudo da tela ativa (compartilhado entre mobile e desktop)
// ---------------------------------------------------------------------------

/**
 * Renderiza a tela correspondente a rota selecionada.
 * Cada tela interna (NovaVisitaScreen, etc) NAO deve ter menu proprio.
 */
@Composable
private fun MainContent(currentRoute: String) {
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
        "relatorios" -> {
            // TODO: RelatoriosScreen()
        }
    }
}

// ---------------------------------------------------------------------------
// Sidebar Desktop
// ---------------------------------------------------------------------------

/**
 * Sidebar fixa lateral conforme Frame 25 do Figma.
 * Itens: Painel, Nova visita, Relatorios, Historico.
 * Rodape: Logout.
 *
 * @param currentRoute Rota atualmente selecionada.
 * @param onNavigate Callback para trocar de tela.
 * @param onLogout Callback para sair do app.
 */
@Composable
private fun DesktopSidebar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(SidebarGreen)
            .padding(vertical = 20.dp)
    ) {
        // Logo + nome
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo GeoSaude",
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "GeoSaude",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Menu de navegacao
        SidebarItem(Icons.Default.Dashboard, "Painel", currentRoute == "dashboard") {
            onNavigate("dashboard")
        }
        SidebarItem(Icons.Default.Assignment, "Nova visita", currentRoute == "nova_visita") {
            onNavigate("nova_visita")
        }
        SidebarItem(Icons.Default.Description, "Relatorios", currentRoute == "relatorios") {
            onNavigate("relatorios")
        }
        SidebarItem(Icons.Default.Schedule, "Historico", currentRoute == "historico") {
            onNavigate("historico")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout
        SidebarItem(Icons.AutoMirrored.Filled.ExitToApp, "Logout", false) {
            onLogout()
        }
    }
}

/**
 * Item individual do menu lateral.
 */
@Composable
private fun SidebarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) GeoSaudeColors.Primary else Color.Transparent
    val textColor = Color.White.copy(alpha = if (isSelected) 1f else 0.7f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .background(bgColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(20.dp), tint = textColor)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = textColor
        )
    }
}