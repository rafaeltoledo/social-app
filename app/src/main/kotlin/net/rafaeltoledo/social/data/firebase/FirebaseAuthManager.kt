package net.rafaeltoledo.social.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.SocialProvider
import net.rafaeltoledo.social.data.model.User
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthManager : AuthManager {

    override suspend fun socialSignIn(token: String, provider: SocialProvider): User =
        suspendCoroutine {
            Firebase.auth.signInWithCredential(
                when (provider) {
                    SocialProvider.GOOGLE -> GoogleAuthProvider.getCredential(token, null)
                    SocialProvider.FACEBOOK -> FacebookAuthProvider.getCredential(token)
                },
            ).addOnCompleteListener { task ->
                handleResult(task, it)
            }
        }

    private fun handleResult(task: Task<AuthResult>, continuation: Continuation<User>) {
        if (task.isSuccessful.not()) {
            continuation.resumeWithException(
                task.exception ?: Exception("No exception was thrown by Firebase"),
            )
        } else {
            continuation.resume(
                User(task.result?.user?.uid ?: error("Expected a user ID")),
            )
        }
    }

    override fun isUserLoggedIn() = Firebase.auth.currentUser != null
}
