package net.rafaeltoledo.social.di

import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.FacebookAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import net.rafaeltoledo.social.data.auth.SocialProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {
    single<DelegatedAuth>(named(SocialProvider.GOOGLE.name)) { GoogleAuth() }
    single<DelegatedAuth>(named(SocialProvider.FACEBOOK.name)) { FacebookAuth() }
}
