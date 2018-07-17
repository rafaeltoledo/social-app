package net.rafaeltoledo.social

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CompletableDeferred
import net.rafaeltoledo.social.data.User
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import net.rafaeltoledo.social.data.auth.SocialProvider
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext

class TestSocialApp : SocialApp() {

    override fun onCreate() {
        super.onCreate()

        // Override value
        // This behavior should be explicit in a future version of Koin
        // See: https://github.com/Ekito/koin/pull/123
        StandAloneContext.loadKoinModules(listOf(
                applicationContext {
                    bean { delegatedAuth }
                    bean { authManager }
                    bean { stringValue }
                }
        ))
    }

    var delegatedAuth: DelegatedAuth = GoogleAuth()
        set(value) {
            StandAloneContext.loadKoinModules(applicationContext { bean { value } })
            field = value
        }

    var authManager: AuthManager = noOpAuthManager
        set(value) {
            StandAloneContext.loadKoinModules(applicationContext { bean { value } })
            field = value
        }

    var stringValue: String = "Social App"
        set(value) {
            StandAloneContext.loadKoinModules(applicationContext { bean { value } })
            field = value
        }

    inline fun <reified T : ViewModel> replaceViewModel(mockViewModel: T) {
        StandAloneContext.loadKoinModules(applicationContext { viewModel { mockViewModel } })
    }
}

val noOpAuthManager = object : AuthManager {

    override fun socialSignIn(token: String, provider: SocialProvider) = CompletableDeferred<User>()

    override fun isUserLoggedIn() = true
}