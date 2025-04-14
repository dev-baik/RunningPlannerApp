package com.android.master.domain.repository

import androidx.paging.PagingData
import com.android.master.domain.model.VideoItem
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun getVideoSearchResult(query: String): Flow<PagingData<VideoItem>>
}