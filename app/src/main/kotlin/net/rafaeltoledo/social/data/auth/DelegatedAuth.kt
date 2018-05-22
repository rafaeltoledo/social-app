package net.rafaeltoledo.social.data.auth

import android.app.Activity
import android.content.Intent

interface DelegatedAuth {

    fun <T : DelegatedAuth> build(activity: Activity): T

    fun signIn(activity: Activity)

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?): AuthResult

    fun signOut(callback: (Status) -> Unit)
}

enum class Status { CANCELED, SUCCESS, FAILURE }

data class AuthResult(val status: Status, val token: String? = null)
