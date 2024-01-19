package com.gallardf.pokedex.data.network.pokemon_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    @SerialName("id")
    val id:Int,
    @SerialName("name")
    val name:String,
    @SerialName("base_happiness")
    val baseHappiness: Int? = 0,
    @SerialName("capture_rate")
    val captureRate:Int? = 0,
    @SerialName("egg_groups")
    val eggsGroup: List<EggsGroupItem>? = null,
    @SerialName("evolution_chain")
    val evolutionChain: EvolutionChainItem?=null
)
