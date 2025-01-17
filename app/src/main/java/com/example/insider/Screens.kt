package com.example.insider

sealed class Screen(val route: String) {
    object MainScreen: Screen("MainScreen")
    object SearchScreen: Screen("SearchScreen")
    object OnlinePlatformScreen: Screen("OnlinePlatformScreen")
}