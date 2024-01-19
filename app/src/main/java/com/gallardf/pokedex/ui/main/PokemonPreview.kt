package com.gallardf.pokedex.ui.main

import com.gallardf.pokedex.domain.mappers.model.PokemonUIModel


object PokemonPreview {
    val pokemon1 = PokemonUIModel(
        1,
        "Charizard",
        "img1",
        false
    )
    val pokemon2 = PokemonUIModel(
        2,
        "Alakazam",
        "img2",
        false
    )
    val pokemon3 = PokemonUIModel(
        3,
        "Bulba",
        "img3",
        false
    )
    val pokemon4 = PokemonUIModel(
        4,
        "Chansey",
        "img4",
        false
    )

    val pokemonList= listOf(pokemon1, pokemon2, pokemon3, pokemon4)
}