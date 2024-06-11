package com.example.movieviewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, movieViewModel: MovieViewModel) {
    //observing state from the viewmodel
    val movies by movieViewModel.filteredMovies
    val isLoading by movieViewModel::isLoading
    val errorMessage by movieViewModel::errorMessage
    val searchQuery by movieViewModel::searchQuery

    //local state to manage TextField inpputs
    var textState by remember { mutableStateOf(TextFieldValue(searchQuery)) }

    //if loading, show loading spinner
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (errorMessage.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage)
            }
        } else {
            //show search bar and movie list
            Column {
                TextField(
                    value = textState,
                    onValueChange = { newTextFieldValue ->
                        textState = newTextFieldValue
                        movieViewModel.onSearchQueryChanged(newTextFieldValue.text) //updating viewmodel with new query
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    placeholder = { Text("Search...") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                //grid of movie items
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(movies.size) { index ->
                        val movie = movies[index]
                        MovieItem(movie = movie) {
                            navController.navigate("details/${movie.id}")
                        }
                    }
                }
            }
        }
    }
}

//ui component that displays individual movie items
@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }, // handle click events
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //loading and displaying movie poster.
        val painter = rememberImagePainter(data = movie.posterUrl)
        Image(
            painter = painter,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )
        //displaying the movie title, handling overflow in case of long title
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
