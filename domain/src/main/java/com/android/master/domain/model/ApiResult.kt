package com.android.master.domain.model

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Fail(val error: Throwable) : ApiResult<Nothing>()
}