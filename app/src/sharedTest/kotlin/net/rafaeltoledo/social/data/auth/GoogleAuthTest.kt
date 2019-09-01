package net.rafaeltoledo.social.data.auth

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import net.rafaeltoledo.social.ui.feature.main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoogleAuthTest {

    @Test
    fun onBuild_createClientWithSuccess() {
        val auth = GoogleAuth()
        val activity = ActivityScenario.launch(MainActivity::class.java)

        activity.onActivity {
            val result = auth.build<GoogleAuth>(it)
            assertThat(result).isNotNull()
        }
    }

    @Test
    fun onBuild_returnsSameInstanceWhenCalledTwice() {
        val auth = GoogleAuth()
        val activity = ActivityScenario.launch(MainActivity::class.java)

        activity.onActivity {
            val first = auth.build<GoogleAuth>(it)
            val second = auth.build<GoogleAuth>(it)
            assertThat(first).isSameInstanceAs(second)
        }
    }
}
