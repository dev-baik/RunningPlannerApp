package com.android.master.data.model.video

import com.google.gson.annotations.SerializedName

data class VideoDetailDTO(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("thumbnail")
    val thumbnail: String
)