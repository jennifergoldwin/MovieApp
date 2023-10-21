package com.programming.movieapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.programming.movieapp.ui.screens.DetailMovieScreen
import com.programming.movieapp.ui.screens.FavoriteMovieScreen
import com.programming.movieapp.ui.screens.HomeScreen
import com.programming.movieapp.ui.viewmodels.MovieViewModel

@Composable
fun BottomBarNavigation(
    navHostController: NavHostController,
    padding: PaddingValues,
    movieViewModel: MovieViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenRoutes.BottomBar.route,
        modifier = Modifier.padding(padding)
    ) {
        navigation(
            route = ScreenRoutes.BottomBar.route,
            startDestination = BottomBarRoutes.HOME.routes
        ) {
            composable(BottomBarRoutes.HOME.routes) {
                HomeScreen(navController = navHostController, viewModel = movieViewModel)
            }
            composable(BottomBarRoutes.FAVORITE.routes) {
                FavoriteMovieScreen(navController = navHostController, viewModel = movieViewModel)
            }
        }
        composable(ScreenRoutes.Detail.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            if (movieId != null) {
                DetailMovieScreen(movieId,movieViewModel)
            } else {
                // Handle invalid movieId
            }
        }
    }
}
