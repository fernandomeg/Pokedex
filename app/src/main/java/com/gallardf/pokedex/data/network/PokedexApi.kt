package com.gallardf.pokedex.data.network

import com.gallardf.pokedex.data.network.pokemon_detail.abilities.PokemonAbilitiesResponse
import com.gallardf.pokedex.data.network.pokemon_detail.PokemonDetailResponse
import com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain.PokemonEvoChainResponse
import com.gallardf.pokedex.data.network.pokemon_list.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit")
        limit: Int = 150,
        @Query("offset")
        offset:Int
    ): PokemonListResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokemonDetail(@Path("name") name:String): PokemonDetailResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonAbilities(@Path("name") name:String): PokemonAbilitiesResponse

    @GET("evolution-chain/{evolution-chain}")
    suspend fun getPokemonEvolutionChain(@Path("evolution-chain") evolutionChain:String): PokemonEvoChainResponse
}