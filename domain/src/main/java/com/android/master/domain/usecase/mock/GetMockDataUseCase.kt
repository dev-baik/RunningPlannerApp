package com.android.master.domain.usecase.mock

import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.MockItem
import com.android.master.domain.repository.MockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMockDataUseCase @Inject constructor(
    private val mockRepository: MockRepository
) {
    operator fun invoke(): Flow<ApiResult<MockItem>> =
        mockRepository.getMockModel()
}