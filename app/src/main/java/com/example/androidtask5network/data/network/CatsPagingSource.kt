package com.example.androidtask5network.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.data.toCat
import com.example.androidtask5network.utils.CATS_STARTING_PAGE_INDEX
import com.example.androidtask5network.utils.MAX_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class CatsPagingSource(
    private val catsService: TheCatApiService
) : PagingSource<Int, Cat>() {

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {

        return try {
            val position = params.key ?: CATS_STARTING_PAGE_INDEX
            val response = catsService.getCats(position, params.loadSize)
            val cats = response.map { it.toCat() }
            val nextKey = if (cats.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = cats,
                prevKey = if (position == CATS_STARTING_PAGE_INDEX) null else position,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}