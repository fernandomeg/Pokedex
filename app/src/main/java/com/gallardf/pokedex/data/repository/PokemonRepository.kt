package com.gallardf.pokedex.data.repository

import com.gallardf.pokedex.data.Resource
import com.gallardf.pokedex.data.datasource.entity.PokemonEntity
import com.gallardf.pokedex.data.network.pokemon_detail.abilities.PokemonAbilitiesResponse
import com.gallardf.pokedex.data.network.pokemon_detail.PokemonDetailResponse
import com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain.PokemonEvoChainResponse

interface PokemonRepository {

    suspend fun getPokemonList(pageSize: Int,offset:Int): Resource<List<PokemonEntity>>

    suspend fun updatePokemonList(pageSize: Int,offset:Int,pokemonEntity: PokemonEntity):Resource<List<PokemonEntity>>

    suspend fun getPokemonDetail(name:String): Resource<PokemonDetailResponse>

    suspend fun getPokemonAbilities(name:String): Resource<PokemonAbilitiesResponse>

    suspend fun getPokemonEvolutionChain(noPokedex:String): Resource<PokemonEvoChainResponse>
}