package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.firebase.FirebaseAuthManager
import org.koin.dsl.module.applicationContext

val firebaseModule = applicationContext {

    bean { FirebaseAuthManager() as AuthManager }
}