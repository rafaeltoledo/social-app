package net.rafaeltoledo.social.ui.feature.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.ui.feature.signin.SignInActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val auth: AuthManager by inject()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this).apply {
            id = R.id.content
            text = mainViewModel.getString()
        })
    }

    override fun onStart() {
        super.onStart()
        if (!auth.isUserLoggedIn()) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}
