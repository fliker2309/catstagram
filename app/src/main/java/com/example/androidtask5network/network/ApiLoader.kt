package com.example.androidtask5network.network

import com.example.androidtask5network.data.entity.Cat
import com.example.androidtask5network.network.model.CatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val api = RetrofitConfig.theCatApiService
private suspend fun getCats() = api.getCats()

suspend fun getCatsList(): List<Cat> = withContext(Dispatchers.IO) {
    return@withContext getCats().map {
        Cat(
            id = it.id,
            photo = it.imageUrl
        )
    }
}