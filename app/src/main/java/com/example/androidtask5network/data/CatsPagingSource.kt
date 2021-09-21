package com.example.androidtask5network.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidtask5network.data.entity.Cat
import com.example.androidtask5network.network.TheCatApiService
import com.example.androidtask5network.utils.CATS_STARTING_PAGE_INDEX
import com.example.androidtask5network.utils.MAX_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class CatsPagingSource(
    private val catsService: TheCatApiService,
    private val query: String
) : PagingSource<Int, Cat>() {
    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {

        val position = params.key ?: CATS_STARTING_PAGE_INDEX
        return try {
            val response = catsService.getCats(query, position, params.loadSize)
            val cats = response.items
            val nextKey = if (cats.isEmpty()) {
                null
            } else {
                position + (params.loadSize / MAX_PAGE_SIZE)
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
        /*  if (query.isEmpty()) {
              return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
          }

          val page: Int = params.key ?: 1
          val pageSize: Int = params.loadSize

         val response = catsService.getCats(query,page,pageSize)
          if(response.isSuccessful)*/
    }
}