package com.example.androidtask5network.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatResponse(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val imageUrl: String
)
