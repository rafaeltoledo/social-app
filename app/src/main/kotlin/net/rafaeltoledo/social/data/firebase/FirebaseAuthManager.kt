package net.rafaeltoledo.social.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.SocialProvider
import net.rafaeltoledo.social.data.model.User

class FirebaseAuthManager : AuthManager {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun socialSignIn(token: String, provider: SocialProvider): User =
        suspendCoroutine {
            when (provider) {
                SocialProvider.GOOGLE -> googleSignIn(token, it)
            }
        }

    private fun googleSignIn(
        token: String,
        continuation: Continuation<User>
    ) {
        auth.signInWithCredential(GoogleAuthProvider.getCredential(token, null))
            .addOnCompleteListener {
                if (it.isSuccessful.not()) {
                    continuation.resumeWithException(it.exception!!)
                } else {
                    continuation.resume(User(it.result!!.user!!.uid))
                }
            }
    }

    override fun isUserLoggedIn() = auth.currentUser != null
}
