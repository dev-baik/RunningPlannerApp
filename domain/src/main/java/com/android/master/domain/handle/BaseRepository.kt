package com.android.master.domain.handle

import com.android.master.domain.model.ApiResult
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    suspend fun <T: Any> safeApiCall(callFunction: suspend () -> Response<T>): ApiResult<T> {
        val response: Response<T>

        try {
            response = callFunction()
        } catch (exception: Exception) {
            return when (exception) {
                is IOException -> ApiResult.Fail(Throwable("Internet error runs"))
                else -> ApiResult.Fail(Throwable(exception))
            }
        }

        val body = response.body()
        return if (body != null) {
            ApiResult.Success(body)
        } else {
            val errorMessage = response.errorBody()?.string() ?: response.message()
            ApiResult.Fail(Throwable(errorMessage))
        }
    }
}
