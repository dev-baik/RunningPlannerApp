package com.android.master.presentation.state

import com.android.master.domain.model.VideoItem

sealed class VideoViewState {
    object Idle : VideoViewState()
    data class Loading(
        val previousItems: List<VideoItem> = emptyList(),
        val keyword: String? = null
    ) : VideoViewState()
    data class Success(
        val videoItems: List<VideoItem>,
        val keyword: String?,
        val isPrevPageAvailable: Boolean
    ) : VideoViewState()
    data class NotFound(val keyword: String) : VideoViewState()
    data class Error(val message: String) : VideoViewState()
}