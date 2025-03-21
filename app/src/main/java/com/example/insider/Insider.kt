package com.example.insider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Insider(
    model: MainViewModel,
    userProfileData: MutableState<UserProfileData>
) {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
         SplashScreen {
            showSplash = false
         }
    } else {
        Navigation(model, userProfileData)
    }
}

@Composable
fun Navigation(
    model: MainViewModel,
    userProfileData: MutableState<UserProfileData>
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ){
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                model = model,
                navigateToSearch = { navController.navigate(route = Screen.SearchScreen.route) },
                navigateToPlatform = { navController.navigate(route = Screen.OnlinePlatformScreen.route) },
                userProfileData = userProfileData
            )
        }

        composable(route = Screen.SearchScreen.route) {
            Search(
                navigateBackToMainScreen = { navController.popBackStack() },
                userProfileData = userProfileData
            )
        }

        composable(route = Screen.OnlinePlatformScreen.route) {
            OnlinePlatform(
                navigateBackToMainScreen = { navController.navigate(route = Screen.MainScreen.route) }
            )
        }
    }
}