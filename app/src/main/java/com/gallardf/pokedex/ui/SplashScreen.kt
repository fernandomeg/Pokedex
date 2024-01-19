package com.gallardf.pokedex.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gallardf.pokedex.R
import com.gallardf.pokedex.ui.navigation.NavigationScreen
import com.gallardf.pokedex.utils.extensions.encodeUrl
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController,pokemonName:String){

    LaunchedEffect(key1 = true){
        delay(5000)
        navController.popBackStack()
        if(pokemonName.isEmpty()){
            navController.navigate(NavigationScreen.MainScreen.route)
        }else{
            navController.navigate(
                NavigationScreen.DetailScreen.createRoute(
                    pokemonName,
                    "url_default"
                )
            )
        }
    }

    Splash()
}

@Composable
fun Splash(){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painterResource(id = R.drawable.ic_pokemon_logo), contentDescription = null,
            modifier = Modifier.padding(horizontal = 30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    Splash()
}