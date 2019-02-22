package net.rafaeltoledo.social.base

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import net.rafaeltoledo.social.TestSocialApp
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    protected val app: TestSocialApp by lazy { ApplicationProvider.getApplicationContext() as TestSocialApp }

    @Before
    fun baseSetUp() {
        Intents.init()
    }

    @After
    fun baseTearDown() {
        Intents.release()
    }
}
