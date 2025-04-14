package com.android.master.presentation.feature.video.model

import com.android.master.domain.model.VideoItem

data class VideoUiModel(
    val id: Int,
    val url: String,
    val height: Int,
    val thumbnail: String
) {
    companion object {
        fun VideoItem.toUiModel() = VideoUiModel(
            id = id,
            url = video.url,
            height = video.height,
            thumbnail = video.thumbnail
        )
    }
}