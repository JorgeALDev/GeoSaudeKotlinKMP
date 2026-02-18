package com.geosaude.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.geosaude.app.di.initKoin

fun main() {
    // Inicializar Koin
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "GeoSaude",
        ) {
            App()
        }
    }
}