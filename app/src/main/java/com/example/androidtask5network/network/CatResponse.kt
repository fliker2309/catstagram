package com.example.androidtask5network.network

import com.example.androidtask5network.data.entity.Cat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatResponse(
    @SerialName("total_count") val total: Int = 0,
    @SerialName("items") val items: List<Cat> = emptyList(),
    /*   ("id")
       val id: Long,
       @SerialName("url")
       val imageUrl: String,
       @SerialName("width")
       val width: Int,
       @SerialName("height")
       val height: Int,
       val nextPage : Int? = null*/
)