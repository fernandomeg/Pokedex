package com.gallardf.pokedex.domain.usecase

import com.gallardf.pokedex.data.Resource
import com.gallardf.pokedex.data.repository.PokemonRepository
import com.gallardf.pokedex.domain.mappers.PokemonMapper
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.PokemonAbilitiesUIModel
import com.gallardf.pokedex.utils.extensions.map
import javax.inject.Inject

class GetPokemonAbilitiesUseCase @Inject constructor(
    private val repository: PokemonRepository,
    private val pokemonMapper: PokemonMapper
) {

    suspend fun getPokemonAbilities(name:String): Resource<PokemonAbilitiesUIModel> {
        return repository.getPokemonAbilities(name)
            .map(
                onSuccess = {
                    val pokemonAbilities = pokemonMapper.toAbilitiesIUModel(it)
                    Resource.Success(pokemonAbilities)
                },
                onError = {
                    Resource.Error(it)
                }
            )
    }

}