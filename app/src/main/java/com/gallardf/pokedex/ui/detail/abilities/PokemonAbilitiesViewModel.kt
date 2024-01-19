package com.gallardf.pokedex.ui.detail.abilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardf.pokedex.domain.usecase.GetPokemonAbilitiesUseCase
import com.gallardf.pokedex.ui.detail.PokemonDetailError
import com.gallardf.pokedex.utils.extensions.onErrorResource
import com.gallardf.pokedex.utils.extensions.onSuccessResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonAbilitiesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPokemonAbilitiesUseCase: GetPokemonAbilitiesUseCase
): ViewModel() {

    private val _state= MutableLiveData<PokemonAbilitiesState>(PokemonAbilitiesState.Loading)
    val state: LiveData<PokemonAbilitiesState> = _state

    init {
        savedStateHandle.get<String>("name")?.let { pokemonName ->
            getPokemonAbilities(pokemonName)
        }
    }

    private fun getPokemonAbilities(name:String) =
        viewModelScope.launch(Dispatchers.IO) {
            getPokemonAbilitiesUseCase.getPokemonAbilities(name)
                .onSuccessResource {
                    val state = PokemonAbilitiesState.Success(it)
                    _state.postValue(state)
                }
                .onErrorResource {
                    val error = PokemonAbilitiesState.Error(PokemonDetailError.CannotLoad())
                    _state.postValue(error)
                }
        }
}