package com.example.androidtask5network.network


import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date
import androidx.annotation.IntRange

interface TheCatApiService  {
    @GET("images/search")
    suspend fun getCats(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
        ): List<CatResponse>






    companion object {

        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
    }
}