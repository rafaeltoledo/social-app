package net.rafaeltoledo.social

import android.app.Application
import net.rafaeltoledo.social.di.authModule
import net.rafaeltoledo.social.di.firebaseModule
import net.rafaeltoledo.social.di.firstModule
import net.rafaeltoledo.social.di.viewModelModule
import org.koin.android.ext.android.startKoin

open class SocialApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
                viewModelModule,
                firstModule,
                authModule,
                firebaseModule
        ))
    }
}
