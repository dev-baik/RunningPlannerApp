package com.android.master.presentation.model

import com.android.master.domain.model.VideoItem

sealed class VideoSearchResponseModel {
    data class Success(val videoItem: List<VideoItem>) : VideoSearchResponseModel()
    data class Error(val message: String) : VideoSearchResponseModel()
}