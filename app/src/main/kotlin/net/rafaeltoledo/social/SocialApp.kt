package net.rafaeltoledo.social

import android.app.Application
import net.rafaeltoledo.social.di.firstModule
import net.rafaeltoledo.social.di.viewModelModule
import org.koin.android.ext.android.startKoin

open class SocialApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(listOf(
                viewModelModule,
                firstModule
        ))
    }
}