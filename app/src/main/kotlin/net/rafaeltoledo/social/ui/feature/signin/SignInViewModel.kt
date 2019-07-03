package net.rafaeltoledo.social.ui.feature.signin

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.FacebookAuth
import net.rafaeltoledo.social.data.auth.GoogleAuth
import net.rafaeltoledo.social.data.auth.SocialProvider
import net.rafaeltoledo.social.data.auth.Status
import net.rafaeltoledo.social.data.model.User
import net.rafaeltoledo.social.ui.BaseViewModel

/**
 * Handles UI state and orchestrates AuthManager implementations
 * based on user selection.
 */
class SignInViewModel(
    private val auth: AuthManager,
    private val googleAuth: DelegatedAuth,
    private val facebookAuth: DelegatedAuth
) : BaseViewModel() {

    val authClient = MutableLiveData<DelegatedAuth>()
    val user = MutableLiveData<User>()

    /**
     * Triggers the auth flow with Google
     * @param activity the Activity object needed for lifecycle
     */
    fun googleSignIn(activity: Activity) {
        loading.value = true

        authClient.value = googleAuth.build(activity)
    }

    /**
     * Triggers the auth flow with Facebook
     * @param activity the Activity object needed for lifecycle
     */
    fun facebookSignIn(activity: Activity) {
        loading.value = true

        authClient.value = facebookAuth.build(activity)
    }

    /**
     * Handles the result returned by the native auth SDKs
     */
    fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        launchDataLoad {
            val result = authClient.value?.onResult(requestCode, resultCode, data)
            if (result?.status == Status.SUCCESS) {
                user.postValue(auth.socialSignIn(
                    token = result.token ?: throw IllegalStateException("Empty token"),
                    provider = authClient.value?.provider() ?: throw IllegalStateException("Empty provider"))
                )
            } else {
                error.postValue(R.string.error_sign_in)
            }
        }
    }

    private fun DelegatedAuth.provider() = when (this) {
        is GoogleAuth -> SocialProvider.GOOGLE
        is FacebookAuth -> SocialProvider.FACEBOOK
        else -> throw IllegalArgumentException("unknown social provider")
    }
}
