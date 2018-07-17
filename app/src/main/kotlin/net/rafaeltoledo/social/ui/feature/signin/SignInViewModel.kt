package net.rafaeltoledo.social.ui.feature.signin

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import kotlinx.coroutines.experimental.launch
import net.rafaeltoledo.social.R
import net.rafaeltoledo.social.data.User
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.DelegatedAuth
import net.rafaeltoledo.social.data.auth.SocialProvider
import net.rafaeltoledo.social.data.auth.Status
import net.rafaeltoledo.social.ui.BaseViewModel

class SignInViewModel(private val auth: AuthManager, private val googleAuth: DelegatedAuth) : BaseViewModel() {

    val authClient = MutableLiveData<DelegatedAuth>()
    val user = MutableLiveData<User>()

    fun googleSignIn(activity: Activity) {
        loading.value = true

        authClient.value = googleAuth.build(activity)
    }

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = authClient.value?.onResult(requestCode, resultCode, data)
        if (result?.status == Status.SUCCESS) {
            launch {
                user.postValue(auth.socialSignIn(result.token!!, SocialProvider.GOOGLE).await())
            }
        } else {
            loading.value = false
            error.postValue(R.string.error_sign_in)
        }
    }
}