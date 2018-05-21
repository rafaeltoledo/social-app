package net.rafaeltoledo.social

import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext.loadKoinModules

class TestSocialApp : SocialApp() {

    fun overrideStringValue(newValue: String) {
        // Override value
        // This behavior should be explicit in a future version of Koin
        // See: https://github.com/Ekito/koin/pull/123
        loadKoinModules(listOf(
                applicationContext {
                    bean { newValue }
                }
        ))
    }
}