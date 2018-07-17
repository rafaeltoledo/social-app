package net.rafaeltoledo.social.data.auth

import kotlinx.coroutines.experimental.Deferred
import net.rafaeltoledo.social.data.User

interface AuthManager {

    fun socialSignIn(token: String, provider: SocialProvider): Deferred<User>

    fun isUserLoggedIn(): Boolean
}

enum class SocialProvider {
    GOOGLE
}
