package com.gallardf.pokedex.domain.mappers

import com.gallardf.pokedex.data.datasource.entity.PokemonEntity
import com.gallardf.pokedex.data.network.pokemon_detail.EggsGroupItem
import com.gallardf.pokedex.data.network.pokemon_detail.EvolutionChainItem
import com.gallardf.pokedex.data.network.pokemon_detail.abilities.PokemonAbilitiesResponse
import com.gallardf.pokedex.data.network.pokemon_detail.abilities.PokemonAbilityItemModel
import com.gallardf.pokedex.data.network.pokemon_detail.abilities.PokemonAbilityModel
import com.gallardf.pokedex.data.network.pokemon_detail.PokemonDetailResponse
import com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain.PokemonEvoChainItemModelResponse
import com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain.PokemonEvoChainResponse
import com.gallardf.pokedex.data.network.pokemon_detail.evolution_chain.PokemonEvoChainSpeciesResponse
import com.gallardf.pokedex.data.network.pokemon_list.PokemonListItemModel
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.EggsGroupUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.EvolutionChainUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.PokemonAbilitiesUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.PokemonAbilityItemUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.PokemonAbilityUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.PokemonDetailUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.evolution_chain.PokemonEvoChainSpeciesUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.evolution_chain.PokemonEvoChainUIItemModel
import com.gallardf.pokedex.domain.mappers.model.detail.evolution_chain.PokemonEvoChainUIModel
import com.gallardf.pokedex.domain.mappers.model.PokemonUIModel
import javax.inject.Inject

class PokemonMapper @Inject constructor(
    private val imageBaseUrl: String
){
    fun toUIModelList(list: List<PokemonEntity>): List<PokemonUIModel>{
        val pokemonModelList = mutableListOf<PokemonUIModel>()
        list.forEach {
            val pokemon = PokemonUIModel(
                id = it.id,
                name = it.name,
                imageUrl = imageBaseUrl.toPokemonImageUrl(it.id),
                isFavorite = it.isFavorite
            )
            pokemonModelList.add(pokemon)
        }
        return pokemonModelList
    }

    fun toEntityList(modelList: List<PokemonListItemModel>): List<PokemonEntity> {
        return modelList.map {
            PokemonEntity(
                it.getId(),
                it.name,
                isFavorite = false
            )
        }
    }

    fun toDetailUIModel(response: PokemonDetailResponse): PokemonDetailUIModel {
        return PokemonDetailUIModel(
            id = response.id,
            name = response.name,
            baseHappiness = response.baseHappiness ?:0,
            captureRate = response.captureRate?:0,
            eggsGroup = toEggsGroupUIModel(response.eggsGroup),
            evolutionChain = toEvolutionChainUIModel(response.evolutionChain)
        )
    }

    fun toEvoChainUIModel(evolution: PokemonEvoChainResponse?): PokemonEvoChainUIModel {
        return evolution?.let {
            PokemonEvoChainUIModel(
                id = it.id,
                chain = toEvoChainItemUIModel(it.chain)
            )
        } ?: PokemonEvoChainUIModel(0,null)
    }

    private fun toEvoChainItemUIModel(chainItem: PokemonEvoChainItemModelResponse?): PokemonEvoChainUIItemModel {
        return chainItem?.let {
            PokemonEvoChainUIItemModel(
                species = toEvoChainSpeciesUIModel(it.species,it.evolvesTo),
            )
        } ?: PokemonEvoChainUIItemModel(emptyList())
    }

    fun toAbilitiesIUModel(response: PokemonAbilitiesResponse): PokemonAbilitiesUIModel {
        return PokemonAbilitiesUIModel(
            abilities = toAbilityItemModel(response.pokemonAbilities)
        )
    }

    private fun toAbilityItemModel(abilityList: List<PokemonAbilityItemModel>?): List<PokemonAbilityItemUIModel>{
        return abilityList?.map {
            PokemonAbilityItemUIModel(
                ability = toAbilityUIModel(it.ability)
            )
        } ?: emptyList()
    }

    private fun toEggsGroupUIModel(eggsGroupItem: List<EggsGroupItem>?): List<EggsGroupUIModel>{
        return eggsGroupItem?.map {
            EggsGroupUIModel(
                name = it.name,
                url = it.url
            )
        } ?: emptyList()
    }

    private fun toAbilityUIModel(ability: PokemonAbilityModel): PokemonAbilityUIModel {
        return ability.let {
            PokemonAbilityUIModel(
                name = it.name,
                url = it.url
            )
        }
    }

    private fun toEvolutionChainUIModel(evolution:EvolutionChainItem?): EvolutionChainUIModel {
        return evolution?.let {
            EvolutionChainUIModel(
                url = it.url
            )
        } ?: EvolutionChainUIModel("Empty")
    }

    private fun toEvoChainSpeciesUIModel(specie:PokemonEvoChainSpeciesResponse?, evolvesTo:List<PokemonEvoChainItemModelResponse>): List<PokemonEvoChainSpeciesUIModel>{
        val list = arrayListOf<PokemonEvoChainSpeciesUIModel>()

        specie.let {
            list.add(PokemonEvoChainSpeciesUIModel(it!!.name))
        }

        val evo = evolvesTo.first()
        evo.let { ev ->
            list.add(PokemonEvoChainSpeciesUIModel(ev.species.name))

        }

        if(evo.evolvesTo.isNotEmpty()){
            evo.let { ev ->
                list.add(PokemonEvoChainSpeciesUIModel(ev.evolvesTo.first().species.name ))
            }
        }

        return list
    }



    private fun PokemonListItemModel.getId(): Int {
        return url.split("/".toRegex()).dropLast(1).last().toInt()
    }

    private fun String.toPokemonImageUrl(pokemonId: Int) =
        "$this${String.format("%03d", pokemonId)}.png"
}