package com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonEvoChainItemModelResponse(
    @SerialName("evolves_to")
    val evolvesTo: List<PokemonEvoChainItemModelResponse>,
    @SerialName("species")
    val species: PokemonEvoChainSpeciesResponse,
)
