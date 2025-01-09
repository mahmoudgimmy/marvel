package com.example.marvel.features.characterdetails.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val images: List<String>
) : Parcelable

