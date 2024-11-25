package com.android.master.runningplannerapp.di

import com.android.master.data.repository.AccountRepositoryImpl
import com.android.master.data.repository.MockRepositoryImpl
import com.android.master.data.repository.VideoRepositoryImpl
import com.android.master.domain.repository.AccountRepository
import com.android.master.domain.repository.MockRepository
import com.android.master.domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMockRepository(repository: MockRepositoryImpl): MockRepository

    @Binds
    abstract fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

    @Binds
    abstract fun bindVideoRepository(repository: VideoRepositoryImpl): VideoRepository
}