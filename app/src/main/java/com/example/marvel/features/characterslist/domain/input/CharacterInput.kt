package com.example.marvel.features.characterslist.domain.input

data class CharacterInput (
    var limit: Int = 20,
    var offset: Int
)