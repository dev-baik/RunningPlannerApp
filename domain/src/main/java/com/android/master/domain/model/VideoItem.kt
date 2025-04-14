package com.android.master.domain.model

import com.android.master.domain.base.DomainModel

data class VideoItem(
    val id: Int,
    val video: Video
) : DomainModel

data class Video(
    val url: String,
    val height: Int,
    val thumbnail: String
) : DomainModel