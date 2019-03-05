package net.rafaeltoledo.social

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import net.rafaeltoledo.social.data.model.User
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import net.rafaeltoledo.social.data.auth.SocialProvider
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin

class SocialAppTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestSocialApp::class.java.name, context)
    }
}

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

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
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

    override suspend fun socialSignIn(token: String, provider: SocialProvider) =
        User("0")

    override fun isUserLoggedIn() = true
}
