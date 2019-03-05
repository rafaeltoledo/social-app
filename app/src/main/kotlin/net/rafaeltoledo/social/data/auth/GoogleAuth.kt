package net.rafaeltoledo.social.data.auth

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import net.rafaeltoledo.social.BuildConfig

class GoogleAuth : DelegatedAuth {

    private var client: GoogleSignInClient? = null

    override fun <T : DelegatedAuth> build(activity: Activity): T {
        if (client == null) {
            val options = GoogleSignInOptions.Builder()
                    .requestIdToken(BuildConfig.GOOGLE_REQUEST_ID_TOKEN)
                    .requestEmail()
                    .build()

            client = GoogleSignIn.getClient(activity, options)
        }

        @Suppress("UNCHECKED_CAST") return this as T
    }

    override fun signIn(activity: Activity) {
        activity.startActivityForResult(client?.signInIntent, RESULT_SIGN_IN)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?): AuthResult {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthResult(Status.SUCCESS, account!!.idToken)
        } catch (e: ApiException) {
            AuthResult(Status.FAILURE)
        }
    }

    override fun signOut(callback: (Status) -> Unit) {
        client?.signOut()?.addOnCompleteListener { callback(if (it.isSuccessful) Status.SUCCESS else Status.FAILURE) }
    }

    companion object {
        const val RESULT_SIGN_IN = 1441
    }
}
