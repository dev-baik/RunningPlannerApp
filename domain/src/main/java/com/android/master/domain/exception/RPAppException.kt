package com.android.master.domain.exception

data class RPAppException(override val message: String?) : Exception(message)
