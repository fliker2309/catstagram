package com.example.androidtask5network.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidtask5network.data.entity.Cat

class EverythingCatsPagingSource(
    private val catsService: TheCatApiService,
    private val query: String
) : PagingSource<Int, Cat>() {
    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

       val response = catsService.getCats(query,page,pageSize)
        if(response.isSuccessful)
    }
}