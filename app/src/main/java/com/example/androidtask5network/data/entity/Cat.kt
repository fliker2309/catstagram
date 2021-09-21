package com.example.androidtask5network.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerialName("id")
    val id: Long = 0,

    @ColumnInfo(name = "photo")
    @SerialName("photo")
    val photo: String
)