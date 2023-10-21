package com.programming.movieapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.programming.movieapp.data.models.Movie

@Composable
fun MovieItem(movie: Movie, posterUrl:String, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .clickable { onItemClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = posterUrl),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .width(200.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Text(text = movie.title, modifier = Modifier.padding(top = 8.dp), fontWeight = FontWeight.Bold)
    }
}