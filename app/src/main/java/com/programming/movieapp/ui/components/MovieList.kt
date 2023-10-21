package com.programming.movieapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.programming.movieapp.data.models.Movie
import com.programming.movieapp.ui.navigation.ScreenRoutes
import com.programming.movieapp.ui.viewmodels.MovieViewModel

@Composable
fun MovieList(navController: NavController, movies: LazyPagingItems<Movie>,viewModel: MovieViewModel) {

    LazyVerticalGrid(columns = GridCells.Fixed(2) ) {
        items(count = movies.itemCount) { idx ->
            movies[idx]?.let { MovieItem(it,viewModel.getPosterUrl(it.posterUrl).toString()){
                navController.navigate(ScreenRoutes.Detail.route.replace("{movieId}",
                    movies[idx]?.id.toString()))
            } }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(100.dp),
//                                color = MaterialTheme.colorScheme.primary
//                            )
                            Text(text = "Loading...", textAlign = TextAlign.Center)
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Loading...")
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    // Handle error state
                }
                loadState.append is LoadState.Error -> {
                    // Handle error state
                }
            }
        }
    }

}


