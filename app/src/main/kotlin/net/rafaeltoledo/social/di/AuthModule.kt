package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import org.koin.dsl.module.applicationContext

val authModule = applicationContext {
    bean { GoogleAuth() as DelegatedAuth }
}