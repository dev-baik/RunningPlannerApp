package com.android.master.data.source.remote.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.master.domain.base.paging.PagedData
import com.android.master.domain.exception.EmptyListException
import com.android.master.domain.exception.RPAppException
import com.android.master.domain.model.VideoItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException

class RPAppPagingSource(
    private val executor: suspend (Int, Int) -> PagedData<List<VideoItem>>
) : PagingSource<Int, VideoItem>() {

    private val seenIds = mutableSetOf<Int>()

    override fun getRefreshKey(state: PagingState<Int, VideoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoItem> {
        val page = params.key ?: INITIAL_PAGE
        val loadSize = params.loadSize

        return try {
            val result = executor.invoke(page, loadSize)
            val filteredData = result.data.filter { seenIds.add(it.id) }

            if (filteredData.isEmpty()) return LoadResult.Error(EmptyListException)

            LoadResult.Page(
                data = filteredData,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (page == LAST_PAGE) null else page + 1
            )
        } catch (e: HttpException) {
            val rawData = e.response()?.errorBody()?.string() ?: ""
            val builder = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<RPAppException> =
                builder.adapter(RPAppException::class.java)
            val exception = jsonAdapter.fromJson(rawData)
            val message = exception?.message
            LoadResult.Error(RPAppException(message))
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val LAST_PAGE = 50
    }
}