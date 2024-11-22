package com.android.master.data.remote

import com.android.master.data.BuildConfig
import com.android.master.data.model.video.VideoSearchApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("api/videos/")
    suspend fun getVideoSearchResult(
        @Query("key") key: String = BuildConfig.PIXABAY_API_KEY,
        @Query("q") query: String,
        @Query("lang") lang: String = "ko",
        @Query("video_type") type: String = "all",
        @Query("category") category: String = "nature",
        @Query("safesearch") safesearch: Boolean = true,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 50,
    ): Response<VideoSearchApiResponse>
}