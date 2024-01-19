package com.gallardf.pokedex.data.network.pokemon_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListItemModel(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url:String
)
