package com.example.androidtask5network.feature_catlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cat(
    @PrimaryKey
    val id: String,
    val url: String
)
