package com.example.androidtask5network.feature_catlist.domain.repository

import com.example.androidtask5network.feature_catlist.data.network.model.CatResponse


interface CatsRepository {
    suspend fun getCatsList(
        page: Int,
        pageSize: Int
    ): List<CatResponse>
}

