package com.android.master.runningplannerapp.di

import com.android.master.data.datasource.mock.MockDataSource
import com.android.master.data.datasource.mock.MockDataSourceImpl
import com.android.master.data.repository.AccountRepositoryImpl
import com.android.master.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsMockDataSource(mockDataSourceImpl: MockDataSourceImpl): MockDataSource

    @Binds
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository
}