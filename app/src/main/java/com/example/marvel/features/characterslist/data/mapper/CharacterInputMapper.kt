package com.example.marvel.features.characterslist.data.mapper

import com.example.marvel.core.utilities.mappers.BaseMapper
import com.example.marvel.features.characterslist.domain.input.CharacterInput
import javax.inject.Inject

class CharacterInputMapper @Inject constructor() : BaseMapper<CharacterInput, Map<String, String>> {
    override fun map(inputModel: CharacterInput): Map<String, String> {
        return mapOf(
            "limit" to  inputModel.limit.toString() ,
            "offset" to inputModel.offset.toString()
        )
    }
}