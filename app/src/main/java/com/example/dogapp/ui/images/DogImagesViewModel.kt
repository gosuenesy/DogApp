package com.example.dogapp.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogapp.ui.images.DogImageRepository

class DogImagesViewModel : ViewModel() {
    private val repository = DogImageRepository()
    val itemsLiveData: LiveData<ArrayList<String>> = repository.itemsLiveData
    //val itemsLiveData: LiveData<ArrayList<DogImage>> = repository.itemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        //reload()
    }

    fun reload(breedName: String) {
        repository.getPosts(breedName)
    }

    operator fun get(index: Int): String? {
        return itemsLiveData.value?.get(index)
    }
}