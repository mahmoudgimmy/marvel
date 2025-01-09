package com.example.marvel.core.utilities.mappers

fun interface BaseMapper<InputModel, OutputModel> {

    fun map(inputModel: InputModel): OutputModel
}