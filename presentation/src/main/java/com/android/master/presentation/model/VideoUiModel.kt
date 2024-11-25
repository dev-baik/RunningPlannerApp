package com.android.master.presentation.model

import com.android.master.domain.model.VideoSearchItem

sealed class VideoUiModel {
    data class Error(val message: String) : VideoUiModel()
    data class VideoNotFound(val keyword: String) : VideoUiModel()
    data class VideoList(val videoItem: VideoSearchItem) : VideoUiModel()
}