package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import org.koin.dsl.module.module

val authModule = module {
    single<DelegatedAuth> { GoogleAuth() }
}
