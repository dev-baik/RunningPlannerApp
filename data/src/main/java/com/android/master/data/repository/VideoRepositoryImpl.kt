package com.android.master.data.repository

import com.android.master.data.datasource.video.VideoDataSource
import com.android.master.data.mapper.VideoMapper.toDomain
import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.VideoSearchItem
import com.android.master.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoDataSource: VideoDataSource
) : VideoRepository {

    override fun searchVideo(
        query: String,
        page: Int
    ): Flow<ApiResult<VideoSearchItem>> {
        return videoDataSource.searchVideo(
            query = query,
            page = page
        ).map {
            it.toDomain()
        }
    }
}