package com.android.master.domain.usecase.video

import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.VideoSearchItem
import com.android.master.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchVideoByKeywordUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    operator fun invoke(keyword: String, page: Int): Flow<ApiResult<VideoSearchItem>> {
        return videoRepository.searchVideo(query = keyword, page = page)
    }
}