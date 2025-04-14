package com.android.master.domain.usecase.video

import androidx.paging.PagingData
import com.android.master.domain.model.VideoItem
import com.android.master.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoSearchResult @Inject constructor(
    private val videoRepository: VideoRepository
) {
    operator fun invoke(query: String): Flow<PagingData<VideoItem>> =
        videoRepository.getVideoSearchResult(query)
}