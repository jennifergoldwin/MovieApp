package com.programming.movieapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.programming.movieapp.data.models.Movie
import com.programming.movieapp.ui.navigation.ScreenRoutes
import com.programming.movieapp.ui.viewmodels.MovieViewModel

@Composable
fun MovieCarousel(navController: NavController, movies: LazyPagingItems<Movie>, viewModel: MovieViewModel) {
    val loadingState = movies.loadState

    if (loadingState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else if (loadingState.append is LoadState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...")
        }
    } else {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(count = movies.itemCount) { idx ->
                movies[idx]?.let { MovieItem(it, viewModel.getPosterUrl(it.posterUrl).toString()) {
                    navController.navigate(
                        ScreenRoutes.Detail.route.replace("{movieId}", movies[idx]?.id.toString())
                    )
                } }
            }
        }
    }
}



