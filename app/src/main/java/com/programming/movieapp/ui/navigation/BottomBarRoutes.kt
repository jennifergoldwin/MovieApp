package com.programming.movieapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomBarRoutes(
    val id: Int,
    val title: String,
    val routes: String,
    val icon: ImageVector
) {

    HOME(1, "Home", "/home", Icons.Default.Home),
    FAVORITE(2, "Favorites", "/favorite", Icons.Default.Favorite)

}