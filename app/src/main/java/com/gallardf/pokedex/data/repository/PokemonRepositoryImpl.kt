package com.gallardf.pokedex.data.repository

import com.gallardf.pokedex.data.Resource
import com.gallardf.pokedex.data.datasource.dao.PokemonDao
import com.gallardf.pokedex.data.datasource.entity.PokemonEntity
import com.gallardf.pokedex.data.network.PokedexApi
import com.gallardf.pokedex.data.network.pokemon_detail.abilities.PokemonAbilitiesResponse
import com.gallardf.pokedex.data.network.pokemon_detail.PokemonDetailResponse
import com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain.PokemonEvoChainResponse
import com.gallardf.pokedex.domain.mappers.PokemonMapper
import com.gallardf.pokedex.utils.extensions.map
import com.gallardf.pokedex.utils.extensions.onSuccessResourceSuspend
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokedexApi: PokedexApi,
    private val pokemonDao: PokemonDao,
    private val pokemonMapper: PokemonMapper
) : PokemonRepository, Repository() {

    override suspend fun getPokemonList(pageSize: Int, offset: Int): Resource<List<PokemonEntity>> {
        val pokemonListDb = pokemonDao.getPokemonList(pageSize,offset)

        return if(pokemonListDb.isNotEmpty()){
            Resource.Success(pokemonListDb)
        }else{
            sendPokemonListRequest(pageSize, offset)
        }
    }

    override suspend fun updatePokemonList(
        pageSize: Int,
        offset: Int,
        pokemonEntity: PokemonEntity
    ): Resource<List<PokemonEntity>> {
        pokemonDao.update(pokemonEntity).let {
            val pokemonListDb = pokemonDao.getPokemonList(pageSize,offset)

            return if(pokemonListDb.isNotEmpty()){
                Resource.Success(pokemonListDb)
            }else{
                sendPokemonListRequest(pageSize, offset)
            }
        }
    }

    private suspend fun sendPokemonListRequest(
        pageSize: Int,
        offset: Int
    ): Resource<List<PokemonEntity>> {
        return networkCall { pokedexApi.getPokemonList(pageSize, offset) }
            .map(
                onSuccess = {
                    val entityList = pokemonMapper.toEntityList(it.pokemonList)
                    Resource.Success(entityList)
                },
                onError = {
                    Resource.Error(it)
                }
            ).onSuccessResourceSuspend {
                pokemonDao.insert(it)
            }
    }

    override suspend fun getPokemonDetail(name: String): Resource<PokemonDetailResponse> {
        return networkCall {pokedexApi.getPokemonDetail(name)}
            .map(
                onSuccess = {
                    Resource.Success(it)
                },
                onError = {
                    Resource.Error(it)
                }
            )
    }

    override suspend fun getPokemonAbilities(name: String): Resource<PokemonAbilitiesResponse> {
        return networkCall { pokedexApi.getPokemonAbilities(name) }
            .map(
                onSuccess = {
                    Resource.Success(it)
                },
                onError = {
                    Resource.Error(it)
                }
            )
    }

    override suspend fun getPokemonEvolutionChain(noPokedex: String): Resource<PokemonEvoChainResponse> {
        return networkCall { pokedexApi.getPokemonEvolutionChain(noPokedex) }
            .map(
                onSuccess = {
                    Resource.Success(it)
                },
                onError = {
                    Resource.Error(it)
                }
            )
    }

}