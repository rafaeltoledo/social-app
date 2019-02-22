package net.rafaeltoledo.social.di

import org.koin.dsl.module.module

val firstModule = module {
    single { "Social App" }
}
