package com.silence.experimental.common.presentation

data class ViewState<T>(
    var data: T? = null,
    var isLoading: Boolean = false,
    var errorMessage: String? = null
)