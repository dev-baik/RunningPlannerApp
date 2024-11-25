package com.android.master.data.mapper

import com.android.master.data.model.video.VideoDetailDTO
import com.android.master.data.model.video.VideoSearchApiResponse
import com.android.master.data.model.video.VideoSizeDTO
import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.VideoDetail
import com.android.master.domain.model.VideoItem
import com.android.master.domain.model.VideoSearchItem
import com.android.master.domain.model.VideoSize

object VideoMapper {

    fun ApiResult<VideoSearchApiResponse>.toDomain(): ApiResult<VideoSearchItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(
                VideoSearchItem(videoItemList = this.data.toVideoItems())
            )

            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    private fun VideoSearchApiResponse.toVideoItems(): List<VideoItem> {
        return this.contents.map { videoItem ->
            VideoItem(
                id = videoItem.id,
                videos = videoItem.videos.toVideoSizes()
            )
        }
    }

    private fun VideoSizeDTO.toVideoSizes(): VideoSize {
        return VideoSize(
            tiny = this.tiny.toVideoDetail()
        )
    }

    private fun VideoDetailDTO.toVideoDetail(): VideoDetail {
        return VideoDetail(
            url = this.url,
            height = this.height,
            thumbnail = this.thumbnail
        )
    }
}