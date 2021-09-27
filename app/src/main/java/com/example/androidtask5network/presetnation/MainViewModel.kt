package com.example.androidtask5network.presetnation

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.data.network.CatsPagingSource
import com.example.androidtask5network.data.network.RetrofitConfig.theCatApiService
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

private val _cat = MutableLiveData<Cat>()
    val cat : LiveData<Cat>
    get() = _cat

    val flow = Pager(PagingConfig(10)){
        CatsPagingSource(theCatApiService)
    }.flow.cachedIn(viewModelScope)


    fun getCat(id : String){
        viewModelScope.launch {
            try {
                val singleCat = theCatApiService.getCatById(id)
                _cat.value = singleCat
            } catch (e: Exception){}
        }
    }
}
