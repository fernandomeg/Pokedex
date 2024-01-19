package com.gallardf.pokedex.utils.extensions

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.gallardf.pokedex.ui.theme.PokedexTheme

inline fun ComponentActivity.ui(
    crossinline content: @Composable () -> Unit
) {
    setContent {
        PokedexTheme {
            content()
        }
    }
}