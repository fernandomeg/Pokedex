package com.gallardf.pokedex.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardf.pokedex.domain.usecase.GetPokemonAbilitiesUseCase
import com.gallardf.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.gallardf.pokedex.utils.extensions.onErrorResource
import com.gallardf.pokedex.utils.extensions.onSuccessResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
): ViewModel() {

    private val _state= MutableLiveData<PokemonDetailState>(PokemonDetailState.Loading)
    val state: LiveData<PokemonDetailState> = _state

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl : LiveData<String> = _imageUrl

    init {
        savedStateHandle.get<String>("name")?.let { pokemonName ->
            getPokemonDetail(pokemonName)
        }
        savedStateHandle.get<String>("imageUrl")?.let { imageUrl ->
            _imageUrl.value = imageUrl
        }
    }

    private fun getPokemonDetail(name:String) =
        viewModelScope.launch(Dispatchers.IO) {
            getPokemonDetailUseCase.getPokemonDetail(name)
                .onSuccessResource {
                    val state = PokemonDetailState.Success(it)
                    _state.postValue(state)
                }
                .onErrorResource {
                    val error = PokemonDetailState.Error(PokemonDetailError.CannotLoad())
                    _state.postValue(error)
                }
        }
}