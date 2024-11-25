package com.android.master.domain.repository

import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.VideoSearchItem
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun searchVideo(
        query: String,
        page: Int,
    ): Flow<ApiResult<VideoSearchItem>>
}