package com.example.marvel.core.network.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("data") val data: Data<T>
)

data class Data<T>(
    @SerializedName("offset") val offset: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("results") val results: T,
    @SerializedName("count") val count: Int?)