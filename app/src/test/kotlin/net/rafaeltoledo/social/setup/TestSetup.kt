package net.rafaeltoledo.social.setup

import net.rafaeltoledo.social.TestSocialApp
import org.junit.After
import org.koin.standalone.StandAloneContext.closeKoin
import org.robolectric.RuntimeEnvironment

abstract class BaseJvmTest {

    protected val app: TestSocialApp by lazy { RuntimeEnvironment.application as TestSocialApp }

    @After
    fun releaseKoin() {
        closeKoin()
    }
}