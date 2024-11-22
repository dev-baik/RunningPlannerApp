package com.android.master.data.mapper

import com.android.master.data.model.mock.MockApiResponse
import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.MockItem

object MockMapper {

    fun ApiResult<MockApiResponse>.mockDataToDomain(): ApiResult<MockItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(
                MockItem(data = data.data)
            )

            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }
}