package net.rafaeltoledo.social

import com.facebook.FacebookSdk
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.FacebookAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import net.rafaeltoledo.social.data.auth.SocialProvider
import net.rafaeltoledo.social.data.model.User
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class TestSocialApp : SocialApp() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.setAutoInitEnabled(false)
        loadKoinModules(
            listOf(
                module(override = true) {
                    single(named(SocialProvider.GOOGLE.name)) { googleAuth }
                    single(named(SocialProvider.FACEBOOK.name)) { fbAuth }
                    single { authManager }
                    single { stringValue }
                }
            )
        )
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

    var googleAuth: DelegatedAuth = GoogleAuth()
        set(value) {
            loadKoinModules(module { single(named(SocialProvider.GOOGLE.name), override = true) { value } })
            field = value
        }

    var fbAuth: DelegatedAuth = FacebookAuth()
        set(value) {
            loadKoinModules(module { single(named(SocialProvider.FACEBOOK.name), override = true) { value } })
            field = value
        }

    var authManager: AuthManager = noOpAuthManager
        set(value) {
            loadKoinModules(module { single(override = true) { value } })
            field = value
        }

    var stringValue: String = "Social App"
        set(value) {
            loadKoinModules(module { single(override = true) { value } })
            field = value
        }
}

val noOpAuthManager = object : AuthManager {

    override suspend fun socialSignIn(token: String, provider: SocialProvider) =
        User("0")

    override fun isUserLoggedIn() = true
}
