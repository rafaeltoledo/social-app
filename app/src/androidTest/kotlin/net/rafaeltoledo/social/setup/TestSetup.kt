package net.rafaeltoledo.social.setup

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import net.rafaeltoledo.social.TestSocialApp

abstract class BaseInstrumentedTest {

    protected val app: TestSocialApp by lazy { ApplicationProvider.getApplicationContext() as TestSocialApp }
}

class SocialAppTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestSocialApp::class.java.name, context)
    }
}