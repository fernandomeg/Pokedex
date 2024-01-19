package com.gallardf.pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gallardf.pokedex.ui.main.MainScreen
import com.gallardf.pokedex.ui.SplashScreen
import com.gallardf.pokedex.ui.detail.abilities.AbilitiesScreen
import com.gallardf.pokedex.ui.detail.evolution_chain.EvolutionaryLineScreen
import com.gallardf.pokedex.ui.detail.abilities.PokemonAbilitiesViewModel
import com.gallardf.pokedex.ui.detail.PokemonDetailScreen
import com.gallardf.pokedex.ui.detail.PokemonDetailViewModel
import com.gallardf.pokedex.ui.detail.evolution_chain.PokemonEvolutionaryLineViewModel
import com.gallardf.pokedex.ui.main.PokemonListViewModel

@Composable
fun AppNavigation(navigationController: NavHostController){

    NavHost(
        navController = navigationController,
        startDestination = NavigationScreen.SplashScreen.route
    ) {
        //Navigation to Splash Screen
        composable(NavigationScreen.SplashScreen.route) {
            SplashScreen(navigationController)
        }

        //Navigation to MainScreen
        composable(NavigationScreen.MainScreen.route) {
            val viewModel = hiltViewModel<PokemonListViewModel>()
            MainScreen(navigationController, viewModel)
        }

        composable(NavigationScreen.DetailScreen.route){
            val viewModel = hiltViewModel<PokemonDetailViewModel>()
            PokemonDetailScreen(navigationController,viewModel)
        }

        composable(NavigationScreen.AbilitiesScreen.route){
            val viewModel = hiltViewModel<PokemonAbilitiesViewModel>()
            AbilitiesScreen(viewModel)
        }

        composable(NavigationScreen.EvolutionaryLineScreen.route){
            val viewModel = hiltViewModel<PokemonEvolutionaryLineViewModel>()
            EvolutionaryLineScreen(viewModel)
        }

    }
}