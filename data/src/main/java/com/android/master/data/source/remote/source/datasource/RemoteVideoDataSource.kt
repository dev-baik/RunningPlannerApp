package com.android.master.data.source.remote.source.datasource

import com.android.master.data.source.remote.dto.responsebody.video.VideoSearchResponse

interface RemoteVideoDataSource {

    suspend fun getVideoSearchResult(query: String, page: Int, perPage: Int): VideoSearchResponse
}