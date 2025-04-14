package com.android.master.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.master.data.source.remote.source.datasource.RemoteVideoDataSource
import com.android.master.data.source.remote.source.paging.RPAppPagingSource
import com.android.master.domain.model.VideoItem
import com.android.master.domain.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val remoteVideoDataSource: RemoteVideoDataSource
) : VideoRepository {

    override fun getVideoSearchResult(query: String): Flow<PagingData<VideoItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                RPAppPagingSource(
                    executor = { page, loadSize ->
                        remoteVideoDataSource.getVideoSearchResult(
                            query = query,
                            page = page,
                            perPage = loadSize
                        ).toDomainModel()
                    }
                )
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }
}