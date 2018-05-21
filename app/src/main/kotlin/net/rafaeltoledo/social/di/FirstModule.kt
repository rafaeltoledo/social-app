package net.rafaeltoledo.social.di

import org.koin.dsl.module.applicationContext

val firstModule = applicationContext {
    bean { "Social App" }
}