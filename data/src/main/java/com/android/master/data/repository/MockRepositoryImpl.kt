package com.android.master.data.repository

import com.android.master.data.datasource.mock.MockDataSource
import com.android.master.data.db.dao.MockDao
import com.android.master.data.db.entity.toDomainModel
import com.android.master.data.mapper.MockMapper.mockDataToDomain
import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.MockItem
import com.android.master.domain.repository.MockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource,
    private val mockDao: MockDao
) : MockRepository {

    override fun getMockModel(): Flow<ApiResult<MockItem>> {
        return mockDataSource.getMockModel().map {
            it.mockDataToDomain()
        }
    }

    override fun getMockData(): Flow<List<MockItem>> {
        return mockDao.getAll().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}