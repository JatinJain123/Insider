package com.example.insider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Insider(model: MainViewModel) {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
         SplashScreen {
            showSplash = false
         }
    } else {
        Navigation(model)
    }
}

@Composable
fun Navigation(model: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ){
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                model = model,
                navigateToSearch = { navController.navigate(route = Screen.SearchScreen.route) },
                navigateToPlatform = { navController.navigate(route = Screen.OnlinePlatformScreen.route) }
            )
        }

        composable(route = Screen.SearchScreen.route) {
            Search(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.OnlinePlatformScreen.route) {
            OnlinePlatform(
                navigateBack = { navController.navigate(route = Screen.MainScreen.route) }
            )
        }
    }
}