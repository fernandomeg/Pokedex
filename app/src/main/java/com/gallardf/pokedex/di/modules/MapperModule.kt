package com.gallardf.pokedex.di.modules

import com.gallardf.pokedex.di.qualifier.ImageBaseUrl
import com.gallardf.pokedex.domain.mappers.PokemonMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {

    @Provides
    fun pokemonMapper(@ImageBaseUrl imageBaseUrl: String): PokemonMapper {
        return PokemonMapper(imageBaseUrl)
    }

}