package com.gallardf.pokedex.data.network.pokemon_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EggsGroupItem(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url:String
)
