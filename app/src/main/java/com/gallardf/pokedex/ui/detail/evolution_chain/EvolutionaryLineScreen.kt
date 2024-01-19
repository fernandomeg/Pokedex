package com.gallardf.pokedex.ui.detail.evolution_chain

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gallardf.pokedex.R
import com.gallardf.pokedex.domain.mappers.model.detail.evolution_chain.PokemonEvoChainUIModel
import com.gallardf.pokedex.ui.theme.GrayLight
import com.gallardf.pokedex.ui.theme.Shapes
import com.gallardf.pokedex.utils.AlertError
import com.gallardf.pokedex.utils.PokemonImage
import com.gallardf.pokedex.utils.ProcessLoading
import com.gallardf.pokedex.utils.extensions.capitalizeFirstChar

@Composable
fun EvolutionaryLineScreen(
    viewModel: PokemonEvolutionaryLineViewModel
) {
    val uiState by viewModel.state.observeAsState()
    uiState?.let {
        when (it) {
            is PokemonEvoChainState.Loading ->
                ProcessLoading()

            is PokemonEvoChainState.Error ->
                AlertError(stringResource(id = it.e.messageResId))

            is PokemonEvoChainState.Success ->
                EvolutionaryLineContent(it.detail)
        }
    }

}

@Composable
fun EvolutionaryLineContent(pokemonEvoChain: PokemonEvoChainUIModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.evolutionary_line_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineLarge
        )

        pokemonEvoChain.chain!!.species!!.forEachIndexed { index, pokemon ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 8.dp),
                shape = Shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GrayLight)
                    ) {
                    PokemonImage(
                        Modifier.size(72.dp)
                            .padding(20.dp,10.dp),
                        "pokemon.imageUrl",
                        pokemon.name
                    )
                    Text(
                        text = pokemon.name.capitalizeFirstChar(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp,10.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }



            }

            if(index != pokemonEvoChain.chain.species!!.lastIndex){
                Image(
                    painterResource(id = R.drawable.ic_down), contentDescription = null,
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally))
            }

        }
    }
}
