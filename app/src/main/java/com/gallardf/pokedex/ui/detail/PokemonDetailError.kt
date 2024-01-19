package com.gallardf.pokedex.ui.detail

import com.gallardf.pokedex.R

sealed class PokemonDetailError(val messageResId: Int) {

    data class CannotLoad(
        val messageId: Int= R.string.pokemon_detail_error_loading
    ): PokemonDetailError(messageId)

}