package net.rafaeltoledo.social.setup

import android.app.Application
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import net.rafaeltoledo.social.TestSocialApp
import org.junit.After
import org.koin.standalone.StandAloneContext

abstract class BaseInstrumentedTest {

    protected val app: TestSocialApp by lazy { InstrumentationRegistry.getTargetContext().applicationContext as TestSocialApp }
}

class SocialAppTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestSocialApp::class.java.name, context)
    }
}