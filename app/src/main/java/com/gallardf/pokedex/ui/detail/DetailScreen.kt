package com.gallardf.pokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gallardf.pokedex.R
import com.gallardf.pokedex.domain.mappers.model.detail.abilities.EggsGroupUIModel
import com.gallardf.pokedex.domain.mappers.model.detail.PokemonDetailUIModel
import com.gallardf.pokedex.ui.navigation.NavigationScreen
import com.gallardf.pokedex.ui.theme.Shapes
import com.gallardf.pokedex.ui.theme.Yellow
import com.gallardf.pokedex.ui.theme.YellowDark
import com.gallardf.pokedex.ui.theme.YellowLight
import com.gallardf.pokedex.utils.AlertError
import com.gallardf.pokedex.utils.PokemonImage
import com.gallardf.pokedex.utils.ProcessLoading
import com.gallardf.pokedex.utils.extensions.capitalizeFirstChar
import com.gallardf.pokedex.utils.extensions.encodeUrl

@Composable
fun PokemonDetailScreen(
    navController: NavHostController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
){
    val uiState by viewModel.state.observeAsState()

    uiState?.let {
        when(it){
            is PokemonDetailState.Loading ->
                ProcessLoading()
            is PokemonDetailState.Error ->
                AlertError(stringResource(id = it.e.messageResId))
            is PokemonDetailState.Success ->
                PokemonDetailContent(it.detail,viewModel, navController)

        }
    }
}

@Composable
fun PokemonDetailContent(pokemonDetail: PokemonDetailUIModel,
                         viewModel: PokemonDetailViewModel = hiltViewModel(),
                         navController: NavHostController){
    BoxWithConstraints {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Yellow,
                        Color.White
                    )
                )
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            viewModel.imageUrl.observeAsState().let {
                PokemonImage(
                    Modifier.size(150.dp),
                    it.value ?: "",
                    pokemonDetail.name
                )
            }

            PokemonInfo(pokemonDetail,navController)
        }


    }
}

@Composable
private fun PokemonInfo(pokemonDetail: PokemonDetailUIModel, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "#${pokemonDetail.id}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = pokemonDetail.name.capitalizeFirstChar(),
            style = MaterialTheme.typography.headlineLarge
        )
        EggsGroupInfo(listEggs = pokemonDetail.eggsGroup)
        SecondaryInfo(pokemonDetail = pokemonDetail, navController = navController)
    }
}

@Composable
private fun EggsGroupInfo(listEggs: List<EggsGroupUIModel>){
    LazyRow(content = {
        items(listEggs){egg ->
            Text(
                modifier = Modifier.padding(8.dp),
                text = egg.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    })
}

@Composable
private fun SecondaryInfo(pokemonDetail: PokemonDetailUIModel, navController: NavHostController){
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .padding(15.dp,15.dp),
        shape = Shapes.large,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(YellowLight),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(modifier = Modifier
                .fillMaxWidth()
                .background(YellowDark)
                .padding(15.dp,10.dp),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.general_info_title),
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall)
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 0.dp),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.happiness_title, pokemonDetail.baseHappiness
                    .toString()),
                style = MaterialTheme.typography.bodyLarge)
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 0.dp, 15.dp, 20.dp),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.capture_rate_title, pokemonDetail.captureRate.toString()),
                style = MaterialTheme.typography.bodyLarge)

            Button(modifier = Modifier
                .background(YellowLight),
                onClick = {
                navController.navigate(
                    NavigationScreen.AbilitiesScreen.createRoute(pokemonDetail.name)) }) {
                Text(stringResource(id = R.string.abilities_title))
            }

            Button(modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp)
                .background(YellowLight),
                onClick = { navController.navigate(
                NavigationScreen.EvolutionaryLineScreen.createRoute(pokemonDetail.evolutionChain.url.encodeUrl())
            ) }) {
                Text(stringResource(id = R.string.evolutionary_line_title))
            }
        }
    }

}