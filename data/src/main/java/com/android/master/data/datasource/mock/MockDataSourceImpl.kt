package com.android.master.data.datasource.mock

import com.android.master.data.model.mock.MockApiResponse
import com.android.master.data.remote.MockApi
import com.android.master.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor(
    private val mockApi: MockApi
) : BaseRepository(), MockDataSource {

    override fun getMockModel(): Flow<ApiResult<MockApiResponse>> {
        return flow {
            safeApiCall {
                mockApi.getMockData()
            }.also { emit(it) }
        }
    }
}