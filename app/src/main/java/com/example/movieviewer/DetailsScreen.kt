package com.example.movieviewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun DetailsScreen(movie: Movie) {
    //column to display movie details
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //load and display movie poster
        val painter = rememberImagePainter(data = movie.posterUrl)
        Image(
            painter = painter,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Fit //adjust content scale to fit whole image
        )
        Spacer(modifier = Modifier.height(16.dp))
        //display various movie details
        Text(text = "Title: ${movie.title}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Year: ${movie.year}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Rated: ${movie.rated}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Plot: ${movie.plot}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Awards: ${movie.awards}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Writer: ${movie.writer}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Country: ${movie.country}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Language: ${movie.language}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Released: ${movie.released}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Runtime: ${movie.runtime}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Metascore: ${movie.metascore}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "IMDB Votes: ${movie.imdbVotes}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "IMDB Rating: ${movie.imdbRating}", style = MaterialTheme.typography.bodyLarge)
    }
}
