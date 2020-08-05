package net.rafaeltoledo.social.data.auth

import android.content.Intent
import androidx.activity.ComponentActivity

interface DelegatedAuth {

    fun <T : DelegatedAuth> build(activity: ComponentActivity): T

    fun signIn(activity: ComponentActivity)

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?): AuthResult

    fun signOut(callback: (Status) -> Unit)
}

enum class Status { CANCELED, SUCCESS, FAILURE }

data class AuthResult(val status: Status, val token: String? = null)
