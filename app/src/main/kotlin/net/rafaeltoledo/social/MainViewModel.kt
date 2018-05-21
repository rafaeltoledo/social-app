package net.rafaeltoledo.social

import android.arch.lifecycle.ViewModel

class MainViewModel(private val string: String) : ViewModel() {

    fun getString() = string
}