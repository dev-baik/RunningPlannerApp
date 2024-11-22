package com.android.master.data.model.video

import com.google.gson.annotations.SerializedName

data class VideoSearchApiResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalVideos: Int,
    @SerializedName("hits")
    val contents: List<VideoListDTO>
)