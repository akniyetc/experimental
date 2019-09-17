package com.silence.experimental.common.extension

import android.content.res.Resources
import com.silence.experimental.R
import com.silence.experimental.common.domain.entity.Failure
import retrofit2.HttpException
import java.net.SocketTimeoutException

fun Failure.errorMessage(resources: Resources) = when (this) {
    is HttpException -> when (this.code()) {
        304 -> resources.getString(R.string.not_modified_error)
        400 -> resources.getString(R.string.bad_request_error)
        401 -> resources.getString(R.string.unauthorized_error)
        403 -> resources.getString(R.string.forbidden_error)
        404 -> resources.getString(R.string.not_found_error)
        405 -> resources.getString(R.string.method_not_allowed_error)
        409 -> resources.getString(R.string.conflict_error)
        422 -> resources.getString(R.string.unprocessable_error)
        500 -> resources.getString(R.string.server_error_error)
        else -> resources.getString(R.string.unknown_error)
    }
    is Failure.NetworkConnection -> resources.getString(R.string.network_error)
    is SocketTimeoutException -> resources.getString(R.string.timeout_error)
    else -> resources.getString(R.string.unknown_error)
}