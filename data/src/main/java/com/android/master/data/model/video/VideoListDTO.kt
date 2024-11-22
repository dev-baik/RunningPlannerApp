package com.android.master.data.model.video

import com.google.gson.annotations.SerializedName

data class VideoListDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("pageURL")
    val pageURL: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("videos")
    val videos: VideoSizeDTO,
    @SerializedName("views")
    val views: Int,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("comment")
    val comments: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user")
    val user: String,
    @SerializedName("userImageURL")
    val userImageURL: String
)
