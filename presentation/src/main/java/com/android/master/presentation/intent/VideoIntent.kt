package com.android.master.presentation.intent

sealed class VideoIntent {
    data class UpdateKeyword(val keyword: String) : VideoIntent()
    object LoadNextPage : VideoIntent()
    object Retry : VideoIntent()
}