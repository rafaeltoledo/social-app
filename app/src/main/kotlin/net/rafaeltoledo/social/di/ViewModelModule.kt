package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.ui.feature.main.MainViewModel
import net.rafaeltoledo.social.ui.feature.signin.SignInViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SignInViewModel(get(), get()) }
}
