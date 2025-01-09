package com.example.marvel.features.characterdetails.di

import com.example.marvel.core.network.services.MarvelServices
import com.example.marvel.features.characterdetails.data.mapper.CategoryInputMapper
import com.example.marvel.features.characterdetails.data.mapper.CategoryResponseMapper
import com.example.marvel.features.characterdetails.data.repository.CharacterDetailsRepoImpl
import com.example.marvel.features.characterdetails.domain.repository.CharacterDetailsRepository
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
    fun provideCharacterDetailsRepository(
        apiServices: MarvelServices,
        categoryInputMapper: CategoryInputMapper,
        categoryResponseMapper: CategoryResponseMapper
    ) = CharacterDetailsRepoImpl(apiServices, categoryInputMapper, categoryResponseMapper)
}

@InstallIn(SingletonComponent::class)
@Module
fun interface CharactersModuleBind {

    @Binds
    fun bindCharacterDetailsRepository(
        characterDetailsRepository: CharacterDetailsRepoImpl
    ): CharacterDetailsRepository
}