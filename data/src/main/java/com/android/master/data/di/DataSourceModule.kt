package com.android.master.data.di

import com.android.master.data.datasource.UserInfoLocalDataSource
import com.android.master.data.datasourceImpl.UserInfoLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsUserInfoLocalDataSource(userInfoLocalDataSourceImpl: UserInfoLocalDataSourceImpl): UserInfoLocalDataSource
}