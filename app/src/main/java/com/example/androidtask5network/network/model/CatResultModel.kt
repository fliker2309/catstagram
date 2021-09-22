package com.example.androidtask5network.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CatResultModel(
    @SerialName("results")
    val results: List<CatResponse>
)
