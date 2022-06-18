package com.example.dogapp.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogapp.DogImage

class FavoritesViewModel : ViewModel() {
    val favoritesData: MutableLiveData<List<DogImage>> = MutableLiveData()
    val sortedData: MutableLiveData<List<DogImage>> = MutableLiveData()
    val tempList = ArrayList<DogImage>()

    operator fun get(index: Int): DogImage? {
        return favoritesData.value?.get(index)
    }

    fun filterBreedName(breedName: String?) {
        if (breedName != "No filter") {
            sortedData.value = favoritesData.value?.filter { it.name == breedName }
        } else {
            sortedData.value = favoritesData.value
        }
    }
}