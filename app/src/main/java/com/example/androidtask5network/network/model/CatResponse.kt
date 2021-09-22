package com.example.androidtask5network.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatResponse(
   @SerialName
      ("id")
   val id: String,
   @SerialName("url")
   val imageUrl: String,
   @SerialName("width")
   val width: Int,
   @SerialName("height")
   val height: Int,
   val nextPage: Int? = null
)