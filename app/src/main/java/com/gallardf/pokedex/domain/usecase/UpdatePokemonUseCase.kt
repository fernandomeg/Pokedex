package com.gallardf.pokedex.domain.usecase

import com.gallardf.pokedex.data.Resource
import com.gallardf.pokedex.data.datasource.entity.PokemonEntity
import com.gallardf.pokedex.data.repository.PokemonRepository
import com.gallardf.pokedex.domain.mappers.PokemonMapper
import com.gallardf.pokedex.domain.mappers.model.PokemonUIModel
import com.gallardf.pokedex.utils.extensions.map
import javax.inject.Inject

class UpdatePokemonUseCase @Inject constructor(
    private val repository: PokemonRepository,
    private val pokemonMapper: PokemonMapper
) {

    private val pageSize = 150
    private var currentPage= 0

    fun getPage()= currentPage

    suspend fun updatePokemon(pokemonEntity: PokemonEntity): Resource<List<PokemonUIModel>> {
        return repository.updatePokemonList(pageSize, pageSize * currentPage, pokemonEntity)
            .map(
                onSuccess = {
                    currentPage++
                    val pokemonModelList = pokemonMapper.toUIModelList(it)
                    Resource.Success(pokemonModelList)
                },
                onError = {
                    Resource.Error(it)
                }
            )
    }

}