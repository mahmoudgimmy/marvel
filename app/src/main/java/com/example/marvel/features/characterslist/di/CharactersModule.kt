package com.example.marvel.features.characterslist.di

import com.example.marvel.core.network.services.MarvelServices
import com.example.marvel.features.characterslist.data.mapper.CharacterInputMapper
import com.example.marvel.features.characterslist.data.mapper.CharacterResponseMapper
import com.example.marvel.features.characterslist.data.repository.CharactersRepositoryImpl
import com.example.marvel.features.characterslist.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersModule {
    @Provides
    @Singleton
    fun provideCharactersRepository(
        apiServices: MarvelServices,
        characterInputMapper: CharacterInputMapper,
        characterResponseMapper: CharacterResponseMapper
    ) = CharactersRepositoryImpl(apiServices, characterInputMapper, characterResponseMapper)
}

@InstallIn(SingletonComponent::class)
@Module
fun interface CharactersModuleBind {

    @Binds
    fun bindCharacterRepository(
        charactersRepository: CharactersRepositoryImpl
    ): CharactersRepository
}