package com.example.marvel.features.characterdetails.domain.input

data class CategoryInput (
    var limit: Int = 20,
    var offset: Int,
    var characterId: Int
)