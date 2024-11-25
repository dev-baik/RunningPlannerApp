package com.android.master.data.model.video

import com.google.gson.annotations.SerializedName

data class VideoSizeDTO(
    @SerializedName("large")
    val large: VideoDetailDTO,
    @SerializedName("medium")
    val medium: VideoDetailDTO,
    @SerializedName("small")
    val small: VideoDetailDTO,
    @SerializedName("tiny")
    val tiny: VideoDetailDTO
)