package com.example.androidtask5network.feature_catlist.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidtask5network.feature_catlist.data.model.Cat

@Database(
    entities = [Cat::class],
    version = 1
)

abstract class CatDatabase : RoomDatabase() {
    abstract val dao: CatDao

    companion object {
        const val DATABASE_NAME = "cats_db"
    }
}
