package com.gallardf.pokedex.ui.main

import com.gallardf.pokedex.domain.mappers.model.PokemonUIModel

sealed class PokemonListState {

    object Loading: PokemonListState()

    data class Error(val e: PokemonListError): PokemonListState()

    data class ListUpdated(
        val list: List<PokemonUIModel>
    ): PokemonListState()

}

