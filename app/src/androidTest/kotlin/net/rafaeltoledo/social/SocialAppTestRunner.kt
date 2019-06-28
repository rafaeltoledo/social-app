package net.rafaeltoledo.social

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.tmurakami.dexopener.DexOpener

/**
 * A custom test runner that setups DexOpener and make all our classes non-final on runtime.
 * Also, it replaces the App instance with a custom one.
 */
class SocialAppTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        DexOpener.install(this)
        return super.newApplication(cl, TestSocialApp::class.java.name, context)
    }
}
