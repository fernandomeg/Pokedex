package com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonEvoChainResponse(
    @SerialName("id")
    val id:Int,
    @SerialName("chain")
    val chain: PokemonEvoChainItemModelResponse
)
