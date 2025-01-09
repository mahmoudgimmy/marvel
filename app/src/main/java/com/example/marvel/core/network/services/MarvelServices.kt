package com.example.marvel.core.network.services

import com.example.marvel.core.network.model.BaseResponse
import com.example.marvel.features.characterdetails.data.dto.response.CategoryResponse
import com.example.marvel.features.characterslist.data.dto.response.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
 interface MarvelServices {
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @QueryMap queryStrings: Map<String, String>
    ): Response<BaseResponse<List<CharactersResponse>>>

    @GET("v1/public/characters/{id}/comics")
    suspend fun getComics(
        @Path("id") id: Int,
        @QueryMap queryStrings: Map<String, String>
    ): Response<BaseResponse<List<CategoryResponse>>>

    @GET("v1/public/characters/{id}/series")
    suspend fun getSeries(
        @Path("id") id: Int,
        @QueryMap queryStrings: Map<String, String>
    ): Response<BaseResponse<List<CategoryResponse>>>

     @GET("v1/public/characters/{id}/stories")
     suspend fun getStories(
         @Path("id") id: Int,
         @QueryMap queryStrings: Map<String, String>
     ): Response<BaseResponse<List<CategoryResponse>>>

     @GET("v1/public/characters/{id}/events")
    suspend fun getEvents(
         @Path("id") id: Int,
        @QueryMap queryStrings: Map<String, String>
    ): Response<BaseResponse<List<CategoryResponse>>>

}