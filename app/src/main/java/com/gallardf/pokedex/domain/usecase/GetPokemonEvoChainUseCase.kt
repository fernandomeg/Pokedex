package com.gallardf.pokedex.domain.usecase

import com.gallardf.pokedex.data.Resource
import com.gallardf.pokedex.data.repository.PokemonRepository
import com.gallardf.pokedex.domain.mappers.PokemonMapper
import com.gallardf.pokedex.domain.mappers.model.detail.evolution_chain.PokemonEvoChainUIModel
import com.gallardf.pokedex.utils.extensions.map
import javax.inject.Inject

class GetPokemonEvoChainUseCase @Inject constructor(
    private val repository: PokemonRepository,
    private val pokemonMapper: PokemonMapper
) {

    suspend fun getPokemonEvolutionChain(noPokedex:String): Resource<PokemonEvoChainUIModel> {
        return repository.getPokemonEvolutionChain(noPokedex)
            .map(
                onSuccess = {
                    val pokemonEvolution = pokemonMapper.toEvoChainUIModel(it)
                    Resource.Success(pokemonEvolution)
                },
                onError = {
                    Resource.Error(it)
                }
            )
    }

}