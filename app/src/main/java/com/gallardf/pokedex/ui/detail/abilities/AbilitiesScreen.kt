package com.gallardf.pokedex.ui.detail.abilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gallardf.pokedex.R
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.PokemonAbilitiesUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.PokemonAbilityItemUIModel
import com.gallardf.pokedex.ui.theme.GrayLight
import com.gallardf.pokedex.utils.AlertError
import com.gallardf.pokedex.utils.ProcessLoading
import com.gallardf.pokedex.utils.extensions.capitalizeFirstChar
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map

@Composable
fun AbilitiesScreen(
    viewModel: PokemonAbilitiesViewModel = hiltViewModel()
){
    val uiState by viewModel.state.observeAsState()
    uiState?.let {
        when(it){
            is PokemonAbilitiesState.Loading ->
                ProcessLoading()
            is PokemonAbilitiesState.Error ->
                AlertError(stringResource(id = it.e.messageResId))
            is PokemonAbilitiesState.Success ->
                PokemonAbilitiesContent(it.detail)
        }
    }
}

@Composable
private fun PokemonAbilitiesContent(pokemonAbilities: PokemonAbilitiesUIModel){
    Column(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()) {
        Text(
            text = stringResource(id = R.string.abilities_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineLarge
        )
        AbilitiesList(abilities = pokemonAbilities.abilities)
    }
}

@Composable
private fun AbilitiesList(
    abilities: List<PokemonAbilityItemUIModel>,
    gridSize: Int = 2){
    val listState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(gridSize),
        content = {
            items(abilities){
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = it.ability.name.capitalizeFirstChar(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(GrayLight)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        contentPadding = PaddingValues(bottom = 20.dp),
        state = listState
    )
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .debounce(300)
            .map {
                (it + 1) * gridSize
            }
    }
}