/*
package com.example.androidtask5network.data.repository

import com.example.androidtask5network.feature_catlist.data.network.TheCatApiService
import com.example.androidtask5network.feature_catlist.data.network.model.CatResponse
import com.example.androidtask5network.feature_catlist.domain.repository.CatsRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val service: TheCatApiService
) : CatsRepository {
    override suspend fun getCatsList(page: Int, pageSize: Int): List<CatResponse> {
        return try {
            val result = service.getCats(
                page = page,
                pageSize = pageSize
            )

        }
    }
}
*/
