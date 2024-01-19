package com.gallardf.pokedex.data.network.pokemon_list

import com.gallardf.pokedex.data.network.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName("results")
    val pokemonList: List<PokemonListItemModel>
): BaseResponse()