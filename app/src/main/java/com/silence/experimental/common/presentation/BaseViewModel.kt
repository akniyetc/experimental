package com.silence.experimental.common.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    protected val parentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + parentJob

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}