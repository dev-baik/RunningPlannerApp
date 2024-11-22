package com.android.master.data.datasource.video

import com.android.master.data.model.video.VideoSearchApiResponse
import com.android.master.data.remote.VideoApi
import com.android.master.domain.handle.BaseRepository
import com.android.master.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoDataSourceImpl @Inject constructor(
    private val videoApi: VideoApi
) : BaseRepository(), VideoDataSource {

    override fun searchVideo(
        query: String,
        page: Int,
    ): Flow<ApiResult<VideoSearchApiResponse>> {
        return flow {
            safeApiCall {
                videoApi.getVideoSearchResult(
                    query = query,
                    page = page
                )
            }.also { emit(it) }
        }
    }
}