package com.example.androidtask5network.presetnation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass){
        MainViewModel::class.java -> MainViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel" )
    } as T
}