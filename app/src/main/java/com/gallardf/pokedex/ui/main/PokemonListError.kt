package com.gallardf.pokedex.ui.main

import com.gallardf.pokedex.R


sealed class PokemonListError(val messageResId: Int) {

    data class CannotLoad(
        val messageId: Int= R.string.pokemon_list_error_loading
    ): PokemonListError(messageId)

}