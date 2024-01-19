package com.gallardf.pokedex.data.network.pokemon_detail.abilities

import com.gallardf.pokedex.data.network.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityItemModel(
    @SerialName("ability")
    val ability: PokemonAbilityModel
):BaseResponse()
