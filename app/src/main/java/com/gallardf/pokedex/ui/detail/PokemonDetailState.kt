package com.gallardf.pokedex.ui.detail

import com.gallardf.pokedex.domain.mappers.model.detail.PokemonDetailUIModel

sealed class PokemonDetailState {
    object Loading : PokemonDetailState()

    data class Error(
        val e: PokemonDetailError
    ) : PokemonDetailState()

    data class Success(
        val detail: PokemonDetailUIModel
    ) : PokemonDetailState()
}
