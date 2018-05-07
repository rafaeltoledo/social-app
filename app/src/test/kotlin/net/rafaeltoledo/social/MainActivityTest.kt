package net.rafaeltoledo.social

import android.widget.TextView
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    fun checkIfActivityIsSuccessfullyCreated() {
        // Arrange
        val newValue = "Test Social App"
        val app = RuntimeEnvironment.application as TestSocialApp
        app.overrideStringValue(newValue)
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        // Act - nothing to do

        // Assert
        val text = activity.findViewById<TextView>(R.id.content)
        assertThat(text.text).isEqualTo(newValue)
    }
}