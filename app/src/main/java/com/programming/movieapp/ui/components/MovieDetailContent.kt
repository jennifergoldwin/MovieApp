package com.programming.movieapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.programming.movieapp.data.models.Movie
import com.programming.movieapp.data.models.MovieDetail
import com.programming.movieapp.ui.viewmodels.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MovieDetailContent(movie: MovieDetail, posterUrl:String, viewModel: MovieViewModel, movieInDAO : Movie?){
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        item {
            VideoPlayer(videoId = movie.videos.results[0].id, lifecycleOwner = lifecycleOwner)
            Row(
                modifier = Modifier.padding(18.dp)
            ) {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(120.dp)
                        .clip(MaterialTheme.shapes.medium),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier
                    .padding(8.dp),) {
                    Text(
                        text = movie.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        Text(text ="Genre: ", fontSize = 14.sp,)
                        LazyRow() {
                            items(movie.genres) { genre ->
                                Text(text = genre.name+" ",fontSize = 14.sp,)
                            }
                        }
                    }

                }
            }
            Text(
                text = movie.synopsis,
                modifier = Modifier
                    .padding(18.dp),
                fontSize = 14.sp
            )
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Default).launch {
                        if (movieInDAO==null){
                            viewModel.addToFav(Movie(movie.id,movie.title,movie.posterUrl))
                        }else{
                            viewModel.deleteFromFav(Movie(movie.id,movie.title,movie.posterUrl))
                        }
                    }
                    movieInDAO?.let { Toast.makeText(context,"Removed from favorite!",Toast.LENGTH_SHORT).show() } ?: run {
                        Toast.makeText(context,"Added to favorite!",Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (movieInDAO!=null){
                    Text(text = "Remove from Favorite")
                }else{
                    Text(text = "Add To Favorite")
                }
            }

        }
    }
}