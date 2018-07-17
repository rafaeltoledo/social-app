package net.rafaeltoledo.social.ui.feature.signin

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.databinding.ActivitySignInBinding
import net.rafaeltoledo.social.ui.feature.main.MainActivity
import org.koin.android.architecture.ext.viewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        binding.buttonGoogleSignIn.setOnClickListener { signInViewModel.googleSignIn(this) }

        observeAuthClient()
        observeViewState()
    }

    private fun observeAuthClient() {
        signInViewModel.authClient.observe(this, Observer {
            it?.signIn(this)
        })

        signInViewModel.user.observe(this, Observer {
            it?.let {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun observeViewState() {
        signInViewModel.loading.observe(this, Observer {
            binding.progress.visibility = if (it!!) View.VISIBLE else View.GONE
            binding.buttonGoogleSignIn.isEnabled = it != true
        })

        signInViewModel.error.observe(this, Observer {
            Snackbar.make(binding.root, it!!, Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        signInViewModel.onResult(requestCode, resultCode, data)
    }
}