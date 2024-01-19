package com.gallardf.pokedex.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gallardf.pokedex.R
import com.gallardf.pokedex.ui.theme.PokedexTheme

@Composable
fun AlertError(message:String){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_alert), contentDescription = null)
        Text(text = message)
    }
}

@Preview(showBackground = true)
@Composable
fun AlertErrorPreview(){
    PokedexTheme(darkTheme = false) {
        AlertError(message = "Error message")
    }
}