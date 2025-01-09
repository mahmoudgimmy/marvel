package com.example.marvel.features.characterdetails.data.dto.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("thumbnail") val thumbnail: Thumbnail?,
    @SerializedName("images") val images: List<Thumbnail>?
)

data class Thumbnail(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
)
