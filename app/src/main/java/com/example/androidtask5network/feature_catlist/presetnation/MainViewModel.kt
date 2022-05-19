package com.example.androidtask5network.feature_catlist.presetnation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.androidtask5network.feature_catlist.data.model.Cat
import com.example.androidtask5network.feature_catlist.data.network.CatsPagingSource
import com.example.androidtask5network.feature_catlist.data.network.RetrofitConfig.theCatApiService
import com.example.androidtask5network.feature_catlist.data.toCat
import com.example.androidtask5network.utils.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.launch

const val TAG = "my_tag"

class MainViewModel : ViewModel() {

    private val _cat = MutableLiveData<Cat>()
    val cat: LiveData<Cat>
        get() = _cat

    val flow = Pager(PagingConfig(DEFAULT_PAGE_SIZE)) {
        CatsPagingSource(theCatApiService)
    }.flow.cachedIn(viewModelScope)

    fun getCat(id: String) {
        viewModelScope.launch {
            // Лучше обработать низкоуровневые ошибки на Data слое, а во ViewModel вернуть модель,
            // или объект специального класса ошибки.
            try {
                val singleCat = theCatApiService.getCatById(id)
                _cat.value = singleCat.toCat()
            } catch (e: Exception) {
                Log.d(TAG, "Exception $e")
            }
        }
    }
}
