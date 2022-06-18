package com.example.dogapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DogViewModel : ViewModel() {
    private val repository = DogRepository()
    val itemsLiveData: LiveData<ArrayList<String>> = repository.itemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    operator fun get(index: Int): String? {
        return itemsLiveData.value?.get(index)
    }
}