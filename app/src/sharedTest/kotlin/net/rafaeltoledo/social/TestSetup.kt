package net.rafaeltoledo.social

import net.rafaeltoledo.social.data.User
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import net.rafaeltoledo.social.data.auth.SocialProvider
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

class TestSocialApp : SocialApp() {

    override fun onCreate() {
        super.onCreate()
        loadKoinModules(listOf(
                module(override = true) {
                    single { delegatedAuth }
                    single { authManager }
                    single { stringValue }
                }
        ))
    }

    var delegatedAuth: DelegatedAuth = GoogleAuth()
        set(value) {
            loadKoinModules(module { single(override = true) { value } })
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

    override suspend fun socialSignIn(token: String, provider: SocialProvider) = User("0")

    override fun isUserLoggedIn() = true
}