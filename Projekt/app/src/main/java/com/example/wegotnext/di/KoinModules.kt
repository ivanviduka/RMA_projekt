package com.example.wegotnext.di

import com.example.wegotnext.model.Game
import com.example.wegotnext.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<Game> { Game() }
}

val viewModelModule = module {
    viewModel { AllGamesViewModel() }
    viewModel { UserGamesViewModel() }
    viewModel{ SplashViewModel() }
    viewModel { LoginViewModel() }
    viewModel { RegistrationViewModel() }
    viewModel { ForgotPasswordViewModel() }
    viewModel { MainActivityViewModel() }
    viewModel { AddNewGameViewModel() }
    viewModel { GameDetailsViewModel() }
}