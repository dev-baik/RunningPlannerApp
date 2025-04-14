package com.android.master.data.di

import com.android.master.data.source.remote.source.datasource.RemoteVideoDataSource
import com.android.master.data.source.remote.source.datasource.UserInfoLocalDataSource
import com.android.master.data.source.remote.source.datasourceImpl.RemoteVideoDataSourceImpl
import com.android.master.data.source.remote.source.datasourceImpl.UserInfoLocalDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindsRemoteVideoDataSource(remoteVideoDataSourceImpl: RemoteVideoDataSourceImpl): RemoteVideoDataSource
}