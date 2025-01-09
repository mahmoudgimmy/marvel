package com.example.marvel.features.characterslist.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val relatedLinks: List<Section>
): Parcelable

@Parcelize
data class Section(val title: String, val url: String): Parcelable