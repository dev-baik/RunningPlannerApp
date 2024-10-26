package com.android.master.domain.usecase.mock

import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.MockItem
import com.android.master.domain.repository.MockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMockDataUseCase @Inject constructor(
    private val mockRepository: MockRepository
) {
    fun getMockModel(): Flow<ApiResult<MockItem>> {
        return mockRepository.getMockModel()
    }

    fun getMockData(): Flow<List<MockItem>> {
        return mockRepository.getMockData()
    }
}