package com.programming.movieapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.programming.movieapp.ui.components.MovieList
import com.programming.movieapp.ui.components.SearchTextField
import com.programming.movieapp.ui.viewmodels.MovieViewModel

@Composable
fun FavoriteMovieScreen(navController: NavController, viewModel: MovieViewModel) {
    val favMovies = viewModel.favMovies.collectAsLazyPagingItems()
    val searchedMoviesInDAO = viewModel.searchedMoviesDAO.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.refreshFavMovies()
    }

    if (favMovies.itemCount<=0){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Favorite Movies",
                textAlign = TextAlign.Center
            )
        }
    }else{
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            SearchTextField(search = searchQuery, onValueChange = {
                searchQuery = it
                viewModel.searchMoviesInDAO(searchQuery)
            } )
            if (searchQuery==""){
                MovieList(navController = navController, movies = favMovies, viewModel)
            }else{
                MovieList(navController = navController, movies = searchedMoviesInDAO, viewModel)
            }
        }

    }

}