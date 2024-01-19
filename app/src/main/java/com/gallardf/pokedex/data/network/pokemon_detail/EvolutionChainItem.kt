package com.gallardf.pokedex.data.network.pokemon_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainItem(
    @SerialName("url")
    val url:String
)
