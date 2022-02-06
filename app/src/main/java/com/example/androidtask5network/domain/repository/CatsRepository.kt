package com.example.androidtask5network.domain.repository

import com.example.androidtask5network.data.network.model.CatResponse

interface CatsRepository {
    suspend fun getCatsList(
        page: Int,
        pageSize: Int
    ): List<CatResponse>
}

