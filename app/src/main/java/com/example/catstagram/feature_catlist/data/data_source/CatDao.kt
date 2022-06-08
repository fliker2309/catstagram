package com.example.catstagram.feature_catlist.data.data_source

import androidx.room.*
import com.example.catstagram.feature_catlist.data.model.Cat
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM cat")
    fun getCats(): Flow<List<Cat>>

    @Query("SELECT * FROM cat WHERE id = :id")
    suspend fun getCatById(id: Int): Cat?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(cat: Cat)

    @Delete
    suspend fun deleteCat(cat: Cat)
}
