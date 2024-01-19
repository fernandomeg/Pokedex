package com.gallardf.pokedex.ui.detail.evolution_chain

import com.gallardf.pokedex.domain.mappers.model.detail.evolution_chain.PokemonEvoChainUIModel
import com.gallardf.pokedex.ui.detail.PokemonDetailError

sealed class PokemonEvoChainState {
    object Loading : PokemonEvoChainState()

    data class Error(
        val e: PokemonDetailError
    ) : PokemonEvoChainState()

    data class Success(
        val detail: PokemonEvoChainUIModel
    ) : PokemonEvoChainState()
}
