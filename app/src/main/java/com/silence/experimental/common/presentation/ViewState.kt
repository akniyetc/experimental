package com.silence.experimental.common.presentation

data class ViewState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)