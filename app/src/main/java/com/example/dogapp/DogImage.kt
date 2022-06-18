package com.example.dogapp

import java.io.Serializable

data class DogImage(val name: String, val image: String?, val isFavorite: Boolean) : Serializable {
}