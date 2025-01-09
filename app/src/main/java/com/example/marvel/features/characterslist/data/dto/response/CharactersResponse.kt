package com.example.marvel.features.characterslist.data.dto.response

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: Thumbnail?,
    @SerializedName("urls") val urls: List<Url>?
)

data class Thumbnail(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
)

data class Url(
    @SerializedName("type") val type: String?,
    @SerializedName("url") val url: String?
)
