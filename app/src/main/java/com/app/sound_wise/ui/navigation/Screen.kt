package com.app.sound_wise.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Splash : Screen("splash")
    object Loading : Screen("loading")
    object Result : Screen("result")
    object Question : Screen("question") {
        fun createRoute(index: Int): String = "$route/$index"
    }
}