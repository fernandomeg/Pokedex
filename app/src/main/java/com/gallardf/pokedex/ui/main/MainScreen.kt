@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.gallardf.pokedex.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gallardf.pokedex.R
import com.gallardf.pokedex.data.datasource.entity.PokemonEntity
import com.gallardf.pokedex.domain.mappers.model.PokemonUIModel
import com.gallardf.pokedex.ui.navigation.NavigationScreen
import com.gallardf.pokedex.ui.theme.GreyLight02
import com.gallardf.pokedex.ui.theme.PokedexTheme
import com.gallardf.pokedex.ui.theme.Shapes
import com.gallardf.pokedex.ui.theme.Yellow
import com.gallardf.pokedex.utils.AlertError
import com.gallardf.pokedex.utils.PokemonImage
import com.gallardf.pokedex.utils.ProcessLoading
import com.gallardf.pokedex.utils.extensions.capitalizeFirstChar
import com.gallardf.pokedex.utils.extensions.encodeUrl
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.observeAsState()

    uiState?.let { state ->
        when (state) {
            PokemonListState.Loading ->
                ProcessLoading()

            is PokemonListState.Error ->
                AlertError(message = stringResource(id = state.e.messageResId))

            is PokemonListState.ListUpdated -> {
                ListContent(
                    pokemonList = state.list,
                    onItemClicked = {
                        navController.navigate(
                            NavigationScreen.DetailScreen.createRoute(
                                it.name,
                                it.imageUrl.encodeUrl()
                            )
                        )
                    },
                    onLongItemClicked = {
                        viewModel.updatePokemon(
                            PokemonEntity(id = it.id,
                                name = it.name,
                                isFavorite = !it.isFavorite))
                        navController.let { nav ->
                            nav.popBackStack()
                            nav.navigate(NavigationScreen.MainScreen.route)
                        }
                    } ,
                    onScroll = {
                        viewModel.showNextPage(it)
                    }
                )

            }
        }
    }
}

@Composable
fun ListContent(
    pokemonList: List<PokemonUIModel>,
    onItemClicked: (PokemonUIModel) -> Unit,
    onLongItemClicked:(PokemonUIModel) -> Unit,
    onScroll: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
    ) {
        PokemonList(list = pokemonList, onItemClicked = onItemClicked, onLongItemClicked = onLongItemClicked, onScroll = onScroll)
    }
}

@Composable
private fun PokemonList(
    modifier: Modifier = Modifier,
    list: List<PokemonUIModel>,
    gridSize: Int = 2,
    onItemClicked: (PokemonUIModel) -> Unit,
    onLongItemClicked:(PokemonUIModel) -> Unit,
    onScroll: (Int) -> Unit) {

    val listState = rememberLazyGridState()

    Text(
        text = stringResource(id = R.string.main_screen_title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineLarge
    )
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(gridSize),
        content = {
            items(list) { pokemon ->
                PokemonItem(pokemon,
                    onItemClicked={onItemClicked(pokemon)},
                    onLongItemClicked={onLongItemClicked(pokemon)})
            }
        },
        contentPadding = PaddingValues(bottom = 10.dp),
        state = listState
    )
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .debounce(300)
            .map { (it + 1) * gridSize }
            .collect { onScroll(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonItem(
    pokemon: PokemonUIModel,
    onItemClicked: () -> Unit,
    onLongItemClicked: () -> Unit) {

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp)
            .combinedClickable(
                onLongClick = {
                    showDialog = true
                },
                onClick = onItemClicked
            ),
        shape = Shapes.large,
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .background(Yellow),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokemonImage(
                Modifier.size(72.dp),
                pokemon.imageUrl,
                pokemon.name
            )
            Row(
                modifier = Modifier
                    .background(GreyLight02)
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = pokemon.name.capitalizeFirstChar(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )

                if(pokemon.isFavorite){
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "End Drawable",
                        modifier = Modifier.padding(8.dp).size(14.dp).align(Alignment.CenterVertically)
                    )
                }
            }
        }

        if (showDialog) {

            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = stringResource(id = R.string.pokemon_favorite_title_dialog))
                },
                text = {
                    Text(text = stringResource(id = R.string.pokemon_favorite_text_dialog))
                },
                confirmButton = {
                    Button(
                        onClick = onLongItemClicked) {
                        Text(
                            text = if (pokemon.isFavorite) stringResource(id = R.string.pokemon_favorite_delete_btn_dialog)
                            else stringResource(id = R.string.pokemon_favorite_add_btn_dialog)
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationScreen.MainScreenPreview() {
    PokedexTheme(darkTheme = true) {
        ListContent(
            pokemonList = PokemonPreview.pokemonList,
            onItemClicked = {},
            onLongItemClicked = {},
            onScroll = {})
    }
}

