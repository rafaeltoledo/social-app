package net.rafaeltoledo.social.ui

import android.widget.TextView
import com.google.common.truth.Truth.assertThat
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.setup.BaseJvmTest
import net.rafaeltoledo.social.ui.feature.main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest : BaseJvmTest() {

    @Test
    fun checkIfActivityIsSuccessfullyCreated() {
        // Arrange
        val newValue = "Test Social App"
        app.stringValue = newValue
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        // Act - nothing to do

        // Assert
        val text = activity.findViewById<TextView>(R.id.content)
        assertThat(text.text).isEqualTo(newValue)
    }
}