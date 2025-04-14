package com.android.master.data.source.remote.dto.responsebody.video

import com.android.master.domain.base.DomainMapper
import com.android.master.domain.base.paging.PagedData
import com.android.master.domain.base.paging.Paging
import com.android.master.domain.model.Video
import com.android.master.domain.model.VideoItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoSearchResponse(
    @field:Json(name = "total")
    val total: Int,
    @field:Json(name = "totalHits")
    val totalHits: Int,
    @field:Json(name = "hits")
    val hits: List<VideoListDTO>
) : DomainMapper<PagedData<List<VideoItem>>> {
    override fun toDomainModel(): PagedData<List<VideoItem>> {
        return PagedData(
            data = hits.map { it.toDomainModel() },
            paging = Paging(
                loadedSize = totalHits
            )
        )
    }
}

@JsonClass(generateAdapter = true)
data class VideoListDTO(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "pageURL")
    val pageURL: String,
    @field:Json(name = "type")
    val type: String,
    @field:Json(name = "tags")
    val tags: String,
    @field:Json(name = "duration")
    val duration: Int,
    @field:Json(name = "videos")
    val videos: VideoSizeDTO,
    @field:Json(name = "views")
    val views: Int,
    @field:Json(name = "downloads")
    val downloads: Int,
    @field:Json(name = "likes")
    val likes: Int,
    @field:Json(name = "comment")
    val comments: Int,
    @field:Json(name = "user_id")
    val userId: Int?,
    @field:Json(name = "user")
    val user: String,
    @field:Json(name = "userImageURL")
    val userImageURL: String
) : DomainMapper<VideoItem> {
    override fun toDomainModel(): VideoItem =
        VideoItem(
            id = id,
            video = videos.tiny.toDomainModel()
        )
}

@JsonClass(generateAdapter = true)
data class VideoSizeDTO(
    @field:Json(name = "large")
    val large: VideoDTO,
    @field:Json(name = "medium")
    val medium: VideoDTO,
    @field:Json(name = "small")
    val small: VideoDTO,
    @field:Json(name = "tiny")
    val tiny: VideoDTO
)

@JsonClass(generateAdapter = true)
data class VideoDTO(
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "width")
    val width: Int,
    @field:Json(name = "height")
    val height: Int,
    @field:Json(name = "size")
    val size: Int,
    @field:Json(name = "thumbnail")
    val thumbnail: String
) : DomainMapper<Video> {
    override fun toDomainModel(): Video =
        Video(
            url = url,
            height = height,
            thumbnail = thumbnail
        )
}