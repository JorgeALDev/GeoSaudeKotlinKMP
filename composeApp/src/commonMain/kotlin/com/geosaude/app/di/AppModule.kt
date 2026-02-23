package com.geosaude.app.di

import com.geosaude.app.presentation.screens.login.LoginViewModel
import com.geosaude.app.presentation.screens.cadastro.CadastroViewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    factory { LoginViewModel() }
    factory { CadastroViewModel() }
}