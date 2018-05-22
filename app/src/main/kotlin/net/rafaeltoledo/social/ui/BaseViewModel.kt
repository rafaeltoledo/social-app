package net.rafaeltoledo.social.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Int>()

    init {
        loading.postValue(false)
    }
}
