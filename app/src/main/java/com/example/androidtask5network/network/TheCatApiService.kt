package com.example.androidtask5network.network


import retrofit2.http.GET
import retrofit2.http.Query
import androidx.annotation.IntRange
import com.example.androidtask5network.network.model.CatResponse
import com.example.androidtask5network.utils.CATS_STARTING_PAGE_INDEX
import com.example.androidtask5network.utils.DEFAULT_PAGE_SIZE
import com.example.androidtask5network.utils.MAX_PAGE_SIZE

interface TheCatApiService  {

    @GET("images/search")
    suspend fun getCats(
        /*  @Query("q") query: String? = null, возможно глянуть тут*/
        @Query("page") page: Int = CATS_STARTING_PAGE_INDEX,
        @Query("limit") @IntRange(
            from = 1,
            to = MAX_PAGE_SIZE.toLong()
        ) pageSize: Int = DEFAULT_PAGE_SIZE
    ): List<CatResponse>
}