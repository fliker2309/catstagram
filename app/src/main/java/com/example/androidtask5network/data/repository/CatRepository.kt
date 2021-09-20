package com.example.androidtask5network.data.repository

import com.example.androidtask5network.data.dao.CatDao
import com.example.androidtask5network.data.entity.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatRepository (private val catDao: CatDao) {

    suspend fun getAllCats() : List<Cat> = withContext(Dispatchers.IO){
        catDao.getAllCats()
    }

    suspend fun insertCatsInDb(cats: List<Cat>) = withContext(Dispatchers.IO){
        catDao.insertCats(cats)
    }
}