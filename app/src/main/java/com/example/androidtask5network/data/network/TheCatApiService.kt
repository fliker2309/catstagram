package com.example.androidtask5network.data.network

import com.example.androidtask5network.data.network.model.CatResponse
import com.example.androidtask5network.presetnation.DEFAULT_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheCatApiService {

    @GET("images/search")
    suspend fun getCats(
        @Query("page") page: Int,
        @Query("limit") pageSize: Int = DEFAULT_PAGE_SIZE
    ): List<CatResponse>

    @GET("images/{image_id}")
    suspend fun getCatById(@Path("image_id") id: String): CatResponse
}
