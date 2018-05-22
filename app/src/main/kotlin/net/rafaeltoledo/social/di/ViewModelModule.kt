package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.ui.feature.main.MainViewModel
import net.rafaeltoledo.social.ui.feature.signin.SignInViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val viewModelModule = applicationContext {
    viewModel { MainViewModel(get()) }
    viewModel { SignInViewModel(get(), get()) }
}