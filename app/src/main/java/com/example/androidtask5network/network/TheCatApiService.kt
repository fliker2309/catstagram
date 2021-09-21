package com.example.androidtask5network.network


import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date
import androidx.annotation.IntRange
import com.example.androidtask5network.utils.DEFAULT_PAGE_SIZE
import com.example.androidtask5network.utils.MAX_PAGE_SIZE

interface TheCatApiService  {

    @GET("images/search")
    suspend fun getCats(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(
            from = 1,
            to = MAX_PAGE_SIZE.toLong()
        ) pageSize: Int = DEFAULT_PAGE_SIZE
    ): CatResponse
}