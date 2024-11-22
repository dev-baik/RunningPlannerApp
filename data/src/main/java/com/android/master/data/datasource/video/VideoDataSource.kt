package com.android.master.data.datasource.video

import com.android.master.data.model.video.VideoSearchApiResponse
import com.android.master.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface VideoDataSource {

    fun searchVideo(
        query: String,
        page: Int,
    ): Flow<ApiResult<VideoSearchApiResponse>>
}