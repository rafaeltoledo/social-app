package net.rafaeltoledo.social.data.auth

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.concurrent.CountDownLatch

/**
 * Authenticates a user using the Facebook Login SDK.
 */
class FacebookAuth : DelegatedAuth {

    private lateinit var callbackManager: CallbackManager
    private lateinit var status: AuthResult
    private lateinit var countDownLatch: CountDownLatch

    private val callback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult) {
            status = AuthResult(Status.SUCCESS, result.accessToken.token)
            countDownLatch.countDown()
        }

        override fun onCancel() {
            status = AuthResult(Status.CANCELED)
            countDownLatch.countDown()
        }

        override fun onError(error: FacebookException) {
            Log.e("FacebookAuth", "Could not complete Facebook signin", error)
            status = AuthResult(Status.FAILURE)
            countDownLatch.countDown()
        }
    }

    override fun <T : DelegatedAuth> build(activity: ComponentActivity): T {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, callback)
        @Suppress("UNCHECKED_CAST")
        return this as T
    }

    override fun signIn(activity: ComponentActivity) {
        countDownLatch = CountDownLatch(1)
        LoginManager.getInstance().logInWithReadPermissions(activity, listOf("email", "public_profile"))
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?): AuthResult {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        countDownLatch.await()
        return status
    }

    override fun signOut(callback: (Status) -> Unit) {
        LoginManager.getInstance().logOut()
        callback(Status.SUCCESS)
    }
}
