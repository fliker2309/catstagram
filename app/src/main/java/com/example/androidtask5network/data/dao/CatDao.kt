package com.example.androidtask5network.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtask5network.data.entity.Cat

@Dao
interface CatDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<Cat>)

    @Query("SELECT * FROM cats")
    fun getAllCats() : List<Cat>
}