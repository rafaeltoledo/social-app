package net.rafaeltoledo.social.data.auth

import android.app.Application
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.facebook.FacebookSdk
import com.google.common.truth.Truth.assertThat
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.ui.feature.main.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FacebookAuthTest {

    @Before
    fun setup() {
        val app: Application = ApplicationProvider.getApplicationContext()
        FacebookSdk.setClientToken(app.getString(R.string.fb_app_id))
        FacebookSdk.sdkInitialize(app)
    }

    @Test
    fun onBuild_createClientWithSuccess() {
        val auth = FacebookAuth()
        val activity = ActivityScenario.launch(MainActivity::class.java)

        activity.onActivity {
            val result = auth.build<FacebookAuth>(it)
            assertThat(result).isNotNull()
        }
    }

    @Test
    fun onBuild_returnsSameInstanceWhenCalledTwice() {
        val auth = FacebookAuth()
        val activity = ActivityScenario.launch(MainActivity::class.java)

        activity.onActivity {
            val first = auth.build<FacebookAuth>(it)
            val second = auth.build<FacebookAuth>(it)
            assertThat(first).isSameInstanceAs(second)
        }
    }
}
