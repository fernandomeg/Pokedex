package com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonEvoChainEvolvesToResponse(
    @SerialName("evolves_to")
    val species: PokemonEvoChainSpeciesResponse
)
