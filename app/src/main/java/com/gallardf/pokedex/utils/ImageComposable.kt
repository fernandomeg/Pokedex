package com.gallardf.pokedex.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gallardf.pokedex.R

@Composable
fun PokemonImage(modifier: Modifier, url: String, desc: String = "") {
    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .placeholder(R.drawable.ic_pokeball)
                .error(R.drawable.ic_pokeball)
                .build()
        ),
        contentDescription = desc,
    )

}