package com.gallardf.pokedex.ui.detail.evolution_chain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardf.pokedex.domain.usecase.GetPokemonEvoChainUseCase
import com.gallardf.pokedex.ui.detail.PokemonDetailError
import com.gallardf.pokedex.ui.detail.abilities.PokemonAbilitiesState
import com.gallardf.pokedex.utils.extensions.onErrorResource
import com.gallardf.pokedex.utils.extensions.onSuccessResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonEvolutionaryLineViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPokemonEvoChainUseCase: GetPokemonEvoChainUseCase
): ViewModel() {

    private val _state= MutableLiveData<PokemonEvoChainState>(PokemonEvoChainState.Loading)
    val state: LiveData<PokemonEvoChainState> = _state

    init {
        savedStateHandle.get<String>("url")?.let { link ->
            val noPokedex = link.substringAfter("evolution-chain/").dropLast(1)
            getPokemonEvolutionChain(noPokedex)
        }
    }

    private fun getPokemonEvolutionChain(noPokedex:String) =
        viewModelScope.launch(Dispatchers.IO) {
            getPokemonEvoChainUseCase.getPokemonEvolutionChain(noPokedex)
                .onSuccessResource {
                    val state = PokemonEvoChainState.Success(it)
                    _state.postValue(state)
                }
                .onErrorResource {
                    val error = PokemonEvoChainState.Error(PokemonDetailError.CannotLoad())
                    _state.postValue(error)
                }
        }

}