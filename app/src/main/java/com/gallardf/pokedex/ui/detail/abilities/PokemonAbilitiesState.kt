package com.gallardf.pokedex.ui.detail.abilities

import com.gallardf.pokedex.domain.mappers.model.detail.abilities.PokemonAbilitiesUIModel
import com.gallardf.pokedex.ui.detail.PokemonDetailError

sealed class PokemonAbilitiesState {
    object Loading : PokemonAbilitiesState()

    data class Error(
        val e: PokemonDetailError
    ) : PokemonAbilitiesState()

    data class Success(
        val detail: PokemonAbilitiesUIModel
    ) : PokemonAbilitiesState()
}
