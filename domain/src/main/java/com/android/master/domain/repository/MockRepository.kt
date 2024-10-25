package com.android.master.domain.repository

import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.MockItem
import kotlinx.coroutines.flow.Flow

interface MockRepository {

    fun getMockModel(): Flow<ApiResult<MockItem>>
}