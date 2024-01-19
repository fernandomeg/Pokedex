package com.gallardf.pokedex.domain.mappers.model

data class PokemonUIModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean
)
