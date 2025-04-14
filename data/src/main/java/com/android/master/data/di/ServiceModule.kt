package com.android.master.data.di

import com.android.master.data.source.remote.service.VideoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideVideoApiService(retrofit: Retrofit): VideoService {
        return retrofit.create(VideoService::class.java)
    }
}