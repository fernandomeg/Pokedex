package com.gallardf.pokedex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardf.pokedex.data.datasource.entity.PokemonEntity
import com.gallardf.pokedex.domain.mappers.model.PokemonUIModel
import com.gallardf.pokedex.domain.usecase.GetPokemonListUseCase
import com.gallardf.pokedex.domain.usecase.UpdatePokemonUseCase
import com.gallardf.pokedex.utils.extensions.mergeWith
import com.gallardf.pokedex.utils.extensions.onErrorResource
import com.gallardf.pokedex.utils.extensions.onSuccessResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val updatePokemonUseCase: UpdatePokemonUseCase
): ViewModel() {

    private val _state = MutableLiveData<PokemonListState>(PokemonListState.Loading)
    val state: LiveData<PokemonListState> = _state

    private var pokemonList = listOf<PokemonUIModel>()

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() =
        viewModelScope.launch(Dispatchers.IO) {
            getPokemonListUseCase.getPokemonList()
                .onSuccessResource {
                    pokemonList = pokemonList.mergeWith(it)
                    _state.postValue(PokemonListState.ListUpdated(pokemonList))
                }
                .onErrorResource {
                    val error = PokemonListError.CannotLoad()
                    _state.postValue(PokemonListState.Error(error))
                }
        }

    fun updatePokemon(pokemonEntity: PokemonEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(PokemonListState.Loading)

            updatePokemonUseCase.updatePokemon(pokemonEntity)
                .onSuccessResource {
                    pokemonList = pokemonList.mergeWith(it)
                    _state.postValue(PokemonListState.ListUpdated(pokemonList))
                }
                .onErrorResource {
                    val error = PokemonListError.CannotLoad()
                    _state.postValue(PokemonListState.Error(error))
                }
        }

    fun showNextPage(itemPosition: Int) {
        if (itemPosition >= pokemonList.size * 0.5) {
            loadPokemonList()
        }
    }
}