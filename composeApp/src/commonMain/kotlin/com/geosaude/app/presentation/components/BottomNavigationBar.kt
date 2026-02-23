package com.geosaude.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosaude.app.presentation.theme.GeoSaudeColors

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("dashboard", Icons.Default.BarChart, "Dashboard"),
        BottomNavItem("visitas", Icons.Default.Description, "Visitas"),
        BottomNavItem("nova_visita", Icons.Default.Assignment, "Nova Visita"),
        BottomNavItem("historico", Icons.Default.Schedule, "Histórico"),
        BottomNavItem("logout", Icons.AutoMirrored.Filled.ExitToApp, "Sair")
    )

    NavigationBar(
        modifier = modifier.height(80.dp),
        containerColor = Color(0xFF2D5F3E),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            val isSelected = selectedRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (item.route == "logout") {
                        onLogout()
                    } else {
                        onNavigate(item.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    if (isSelected) {
                        Text(
                            text = item.label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GeoSaudeColors.Primary,
                    selectedTextColor = GeoSaudeColors.Primary,
                    unselectedIconColor = Color.White.copy(alpha = 0.5f),
                    unselectedTextColor = Color.Transparent,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}