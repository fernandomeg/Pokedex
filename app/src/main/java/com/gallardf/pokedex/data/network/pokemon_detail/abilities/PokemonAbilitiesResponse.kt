package com.gallardf.pokedex.data.network.pokemon_detail.abilities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilitiesResponse(
    @SerialName("abilities")
    val pokemonAbilities: List<PokemonAbilityItemModel>
)
