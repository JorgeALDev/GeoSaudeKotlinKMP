package com.geosaude.app

import androidx.compose.runtime.Composable

// Este arquivo é substituído pelas versões específicas de cada plataforma:
// - androidMain/App.kt (usa NavController - Android only)
// - jvmMain/App.kt (usa state simples - Desktop)
// - iosMain/App.kt (quando implementarmos iOS)

// FUNÇÃO EXPECT/ACTUAL PATTERN (alternativa)
@Composable
expect fun App()