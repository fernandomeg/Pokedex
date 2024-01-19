package com.gallardf.pokedex.ui.navigation

sealed class NavigationScreen(val route:String) {
    object SplashScreen: NavigationScreen("splash_screen")
    object MainScreen: NavigationScreen("main_screen")
    object DetailScreen : NavigationScreen("detail_screen/{name}/{imageUrl}") {
        fun createRoute(pokemonName: String,imageUrl:String): String =
            "detail_screen/${pokemonName}/${imageUrl}"
    }

    object AbilitiesScreen : NavigationScreen("abilities_screen/{name}") {
        fun createRoute(pokemonName: String): String =
            "abilities_screen/${pokemonName}"
    }

    object EvolutionaryLineScreen: NavigationScreen("evolution_screen/{url}") {
        fun createRoute(url: String): String =
            "evolution_screen/${url}"
    }
}