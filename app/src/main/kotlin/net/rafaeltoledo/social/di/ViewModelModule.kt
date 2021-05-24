package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.data.auth.SocialProvider
import net.rafaeltoledo.social.ui.feature.main.MainViewModel
import net.rafaeltoledo.social.ui.feature.signin.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel {
        SignInViewModel(
            get(),
            get(named(SocialProvider.GOOGLE.name)),
            get(named(SocialProvider.FACEBOOK.name)),
        )
    }
}
