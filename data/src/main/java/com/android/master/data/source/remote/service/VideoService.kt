package com.android.master.data.source.remote.service

import com.android.master.data.BuildConfig.PIXABAY_API_KEY
import com.android.master.data.source.remote.dto.responsebody.video.VideoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {

    @GET("api/videos/")
    suspend fun getVideoSearchResult(
        @Query("key") key: String = PIXABAY_API_KEY,
        @Query("q") query: String,
        @Query("lang") lang: String = "ko",
        @Query("video_type") type: String = "all",
        @Query("category") category: String = "nature",
        @Query("safesearch") safesearch: Boolean = true,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): VideoSearchResponse
}