package net.rafaeltoledo.social

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.experimental.async
import net.rafaeltoledo.social.data.User
import net.rafaeltoledo.social.data.auth.*
import net.rafaeltoledo.social.setup.BaseInstrumentedTest
import net.rafaeltoledo.social.ui.feature.signin.SignInActivity
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInActivityTest : BaseInstrumentedTest() {

    @JvmField @Rule
    val activityRule = IntentsTestRule<SignInActivity>(SignInActivity::class.java, false, false)

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
        every { delegatedAuth.signIn(any()) } answers { (it.invocation.args[0] as Activity).startActivityForResult(Intent().putExtra("key", "value"), 0) }
        every { delegatedAuth.onResult(any(), any(), any()) } returns AuthResult(Status.SUCCESS, token = "ok")

        every { authManager.socialSignIn(any(), any()) } returns async { User("1") }
        every { authManager.isUserLoggedIn() } returns true

        activityRule.launchActivity(Intent())

        intending(hasExtras(hasEntry(equalTo("key"), equalTo("value"))))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        // Act
        onView(withId(R.id.button_google_sign_in)).perform(click())

        // Assert
        onView(withText(app.stringValue)).check(matches(isDisplayed()))

        verify(exactly = 1) { delegatedAuth.signIn(activityRule.activity) }
        verify(exactly = 1) { authManager.socialSignIn("ok", SocialProvider.GOOGLE) }

    }

    @Test
    fun shouldDisplayedErrorOnFailedGoogleLogin() {
        // Arrange
        every { delegatedAuth.signIn(any()) } answers { (it.invocation.args[0] as Activity).startActivityForResult(Intent().putExtra("key", "value"), 0) }
        every { delegatedAuth.onResult(any(), any(), any()) } returns AuthResult(Status.FAILURE)

        activityRule.launchActivity(Intent())

        intending(hasExtras(hasEntry(equalTo("key"), equalTo("value"))))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        // Act
        onView(withId(R.id.button_google_sign_in)).perform(click())

        // Assert
        onView(withText(R.string.error_sign_in)).check(matches(isDisplayed()))

        verify(exactly = 1) { delegatedAuth.signIn(activityRule.activity) }
        verify(exactly = 0) { authManager.socialSignIn(any(), any()) }
    }
}

