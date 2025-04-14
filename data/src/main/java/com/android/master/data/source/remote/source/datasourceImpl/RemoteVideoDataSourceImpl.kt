package com.android.master.data.source.remote.source.datasourceImpl

import com.android.master.data.source.remote.dto.responsebody.video.VideoSearchResponse
import com.android.master.data.source.remote.service.VideoService
import com.android.master.data.source.remote.source.datasource.RemoteVideoDataSource
import javax.inject.Inject

class RemoteVideoDataSourceImpl @Inject constructor(
    private val videoService: VideoService
) : RemoteVideoDataSource {

    override suspend fun getVideoSearchResult(query: String, page: Int, perPage: Int): VideoSearchResponse {
        return videoService.getVideoSearchResult(query = query, page = page, perPage = perPage)
    }
}