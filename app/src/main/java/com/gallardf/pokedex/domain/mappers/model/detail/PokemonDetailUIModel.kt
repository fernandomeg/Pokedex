package com.gallardf.pokedex.domain.mappers.model.detail

import com.gallardf.pokedex.domain.mappers.model.detail.abilities.EggsGroupUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.EvolutionChainUIModel

data class PokemonDetailUIModel(
    val id:Int,
    val name:String,
    val baseHappiness: Int,
    val captureRate:Int,
    val eggsGroup: List<EggsGroupUIModel>,
    val evolutionChain: EvolutionChainUIModel
)
