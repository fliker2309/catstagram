package com.example.androidtask5network.network


import retrofit2.http.GET

interface TheCatApiService  {
    @GET("images/search")
    suspend fun getCats(): List<CatResponse>
}