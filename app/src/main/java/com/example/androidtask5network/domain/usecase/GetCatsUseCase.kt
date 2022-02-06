package com.example.androidtask5network.domain.usecase

import com.example.androidtask5network.data.network.model.CatResponse
import com.example.androidtask5network.domain.repository.CatsRepository
import javax.inject.Inject

interface GetCatsUseCase {
    suspend fun execute(
        page: Int,
        pageSize: Int
    ): List<CatResponse>
}

class GetCatsUseCaseImpl @Inject constructor(
    private val repository: CatsRepository
) : GetCatsUseCase {
    override suspend fun execute(page: Int, pageSize: Int): List<CatResponse> =
        repository.getCatsList(page, pageSize)
}
