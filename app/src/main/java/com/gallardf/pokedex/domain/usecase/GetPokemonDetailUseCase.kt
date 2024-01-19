package com.gallardf.pokedex.domain.usecase

import com.gallardf.pokedex.data.Resource
import com.gallardf.pokedex.data.repository.PokemonRepository
import com.gallardf.pokedex.domain.mappers.PokemonMapper
import com.gallardf.pokedex.domain.mappers.model.detail.PokemonDetailUIModel
import com.gallardf.pokedex.utils.extensions.map
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository,
    private val pokemonMapper: PokemonMapper
) {

    suspend fun getPokemonDetail(name:String): Resource<PokemonDetailUIModel>{
        return repository.getPokemonDetail(name).map(
            onSuccess = {
                val detail = pokemonMapper.toDetailUIModel(it)
                Resource.Success(detail)
            },
            onError = {
                Resource.Error(it)
            }
        )
    }
}