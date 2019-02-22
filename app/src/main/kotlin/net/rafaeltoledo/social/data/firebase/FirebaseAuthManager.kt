package net.rafaeltoledo.social.data.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import net.rafaeltoledo.social.data.model.User
import net.rafaeltoledo.social.data.auth.AuthManager
import net.rafaeltoledo.social.data.auth.SocialProvider

class FirebaseAuthManager : AuthManager {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun socialSignIn(token: String, provider: SocialProvider): User {
        if (provider == SocialProvider.GOOGLE) {
            return googleSignIn(token)
        }
        throw NotImplementedError("Unknown provider")
    }

    private fun googleSignIn(token: String): User {
        val task = auth.signInWithCredential(GoogleAuthProvider.getCredential(token, null))
        Tasks.await(task)
        if (task.isSuccessful.not()) {
            throw task.exception!!
        }

        return User(task.result!!.user.uid)
    }

    override fun isUserLoggedIn() = auth.currentUser != null
}
