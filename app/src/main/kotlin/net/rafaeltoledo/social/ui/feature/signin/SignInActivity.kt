package net.rafaeltoledo.social.ui.feature.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.databinding.ActivitySignInBinding
import net.rafaeltoledo.social.ui.feature.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        binding.lifecycleOwner = this
        binding.viewModel = signInViewModel

        observeAuthClient()
        observeViewState()
    }

    private fun observeAuthClient() {
        signInViewModel.authClient.observe(this) {
            it.signIn(this)
        }

        signInViewModel.user.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun observeViewState() {
        signInViewModel.error.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        signInViewModel.onResult(requestCode, resultCode, data)
    }
}
