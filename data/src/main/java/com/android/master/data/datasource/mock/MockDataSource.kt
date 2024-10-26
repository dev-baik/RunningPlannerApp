package com.android.master.data.datasource.mock

import com.android.master.data.model.mock.MockApiResponse
import com.android.master.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface MockDataSource {

    fun getMockModel(): Flow<ApiResult<MockApiResponse>>
}