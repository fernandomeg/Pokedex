package com.gallardf.pokedex.di.modules

import com.gallardf.pokedex.data.repository.PokemonRepository
import com.gallardf.pokedex.data.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun pokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
}