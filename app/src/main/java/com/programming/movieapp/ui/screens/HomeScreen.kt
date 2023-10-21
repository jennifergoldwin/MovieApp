package com.programming.movieapp.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.programming.movieapp.ui.components.MovieCarousel
import com.programming.movieapp.ui.components.MovieList
import com.programming.movieapp.ui.components.SearchTextField
import com.programming.movieapp.ui.viewmodels.MovieViewModel

import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val searchedMovies = viewModel.searchedMovies.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        item {
            SearchTextField(search = searchQuery, onValueChange = {
                searchQuery = it
                viewModel.searchMovies(searchQuery)
            })
        }

        if (searchQuery == "") {
            item {
                Text(
                    text = "Now Playing Movies",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
            }
            item {
                MovieCarousel(navController = navController, movies = nowPlayingMovies, viewModel)
            }
            item {
                Text(
                    text = "Popular Movies",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
            }
            item {
                MovieCarousel(navController = navController, movies = popularMovies, viewModel)
            }
            item {
                Text(
                    text = "Upcoming Movies",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
            }
            item {
                MovieCarousel(navController = navController, movies = upcomingMovies, viewModel)
            }
        } else {
            item {
                MovieList(navController = navController, movies = searchedMovies, viewModel)
            }
        }
    }
}
