package com.gallardf.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.gallardf.pokedex.ui.navigation.AppNavigation
import com.gallardf.pokedex.utils.extensions.ui
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        ui {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxWidth()
            ) { innerPadding ->
                BoxWithConstraints(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    AppNavigation(navController)
                }
            }
        }
    }
}
