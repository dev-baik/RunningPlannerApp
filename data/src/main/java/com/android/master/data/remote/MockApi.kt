package com.android.master.data.remote

import com.android.master.data.model.mock.MockApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface MockApi {

    @GET("")
    fun getMockData(): Response<MockApiResponse>
}