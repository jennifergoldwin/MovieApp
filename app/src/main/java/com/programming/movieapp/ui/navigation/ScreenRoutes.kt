package com.programming.movieapp.ui.navigation

sealed class ScreenRoutes(val route: String) {
    object BottomBar : ScreenRoutes("/bottombar")
    object Detail : ScreenRoutes("/detail/{movieId}")

}