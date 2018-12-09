package net.rafaeltoledo.social.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.rafaeltoledo.social.R

abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Int>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        loading.postValue(false)
    }

    protected fun launchDataLoad(block: suspend CoroutineScope.() -> Unit): Job {
        return uiScope.launch {
            try {
                loading.postValue(true)
                block()
            } catch (error: Exception) {
                errorHandler(error)
            } finally {
                loading.postValue(false)
            }
        }
    }

    private val errorHandler = { e: Throwable ->
        Log.e("BaseViewModel", "Failed to execute", e)
        loading.postValue(false)
        error.postValue(R.string.error_default_message)
    }
}
