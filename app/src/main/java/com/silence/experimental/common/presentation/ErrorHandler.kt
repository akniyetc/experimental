package com.silence.experimental.common.presentation

import android.content.res.Resources
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.extension.errorMessage


class ErrorHandler(private val resources: Resources) {

    fun proceed(error: Failure, messageListener: (String) -> Unit = {}) {
        messageListener(error.errorMessage(resources))
    }
}