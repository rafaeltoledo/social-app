package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.firebase.FirebaseAuthManager
import org.koin.dsl.module.module

val firebaseModule = module {

    single<AuthManager> { FirebaseAuthManager() }
}
