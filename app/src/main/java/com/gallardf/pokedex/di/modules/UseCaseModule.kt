package com.gallardf.pokedex.di.modules

import com.gallardf.pokedex.data.repository.PokemonRepository
import com.gallardf.pokedex.domain.usecase.GetPokemonListUseCase
import com.gallardf.pokedex.domain.mappers.PokemonMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun getPokemonListUseCase(pokemonRepository: PokemonRepository, pokemonMapper: PokemonMapper) =
        GetPokemonListUseCase(pokemonRepository,pokemonMapper)
}