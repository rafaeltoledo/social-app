package net.rafaeltoledo.social.test.ui

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.getIntents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.truth.content.IntentSubject.assertThat
import io.mockk.*
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.base.BaseTest
import net.rafaeltoledo.social.data.User
import net.rafaeltoledo.social.data.auth.*
import net.rafaeltoledo.social.ui.feature.main.MainActivity
import net.rafaeltoledo.social.ui.feature.signin.SignInActivity
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInActivityTest : BaseTest() {

    lateinit var delegatedAuth: DelegatedAuth
    lateinit var authManager: AuthManager

    @Before
    fun setup() {
        authManager = mockk()
        app.authManager = authManager

        delegatedAuth = mockk()
        app.delegatedAuth = delegatedAuth

        every { delegatedAuth.build<DelegatedAuth>(any()) } returns delegatedAuth
    }

    @Test
    fun shouldSignInWhenSuccessGoogleLogin() {
        // Arrange
        every { delegatedAuth.signIn(any()) } answers {
            (it.invocation.args[0] as Activity).startActivityForResult(
                Intent().putExtra("key", "value"),
                0
            )
        }
        every { delegatedAuth.onResult(any(), any(), any()) } returns AuthResult(Status.SUCCESS, token = "ok")

        coEvery { authManager.socialSignIn(any(), any()) } returns User("1")
        every { authManager.isUserLoggedIn() } returns true

        val scenario = ActivityScenario.launch(SignInActivity::class.java)

        intending(hasExtras(hasEntry(equalTo("key"), equalTo("value"))))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        // Act
        onView(withId(R.id.button_google_sign_in)).perform(click())

        // Assert
        assertThat(getIntents().last()).hasComponentClass(MainActivity::class.java)

        scenario.onActivity {
            verify(exactly = 1) { delegatedAuth.signIn(it) }
            coVerify(exactly = 1) { authManager.socialSignIn("ok", SocialProvider.GOOGLE) }
        }
    }

    @Test
    fun shouldDisplayErrorOnFailedGoogleLogin() {
        // Arrange
        every { delegatedAuth.signIn(any()) } answers {
            (it.invocation.args[0] as Activity).startActivityForResult(
                Intent().putExtra("key", "value"),
                0
            )
        }
        every { delegatedAuth.onResult(any(), any(), any()) } returns AuthResult(Status.FAILURE)

        val scenario = ActivityScenario.launch(SignInActivity::class.java)
        intending(hasExtras(hasEntry(equalTo("key"), equalTo("value"))))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        // Act
        onView(withId(R.id.button_google_sign_in)).perform(click())

        // Assert
        onView(withText(R.string.error_sign_in)).check(matches(isDisplayed()))

        scenario.onActivity {
            verify(exactly = 1) { delegatedAuth.signIn(it) }
            coVerify(exactly = 0) { authManager.socialSignIn(any(), any()) }
        }
    }
}
