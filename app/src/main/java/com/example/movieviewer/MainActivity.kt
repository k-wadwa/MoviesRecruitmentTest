package com.example.movieviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.movieviewer.ui.theme.MovieViewerTheme

class MainActivity : ComponentActivity() {
    //create an instance of the viewmodel
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create an instance of ApiService
        val apiService = RetrofitClient.instance.create(ApiService::class.java)

        // Use the ViewModelFactory to create an instance of MovieViewModel
        val viewModelFactory = MovieViewModelFactory(apiService)
        movieViewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)
        //set content view using jetpack compose
        setContent {
            MovieViewerTheme {
                //create a navController for navigation
                val navController = rememberNavController()
                //setup navigatoin graph
                SetupNavGraph(navController = navController, movieViewModel = movieViewModel)
            }
        }
    }
}

//function in charge of setting up nav graph
@Composable
fun SetupNavGraph(navController: NavHostController, movieViewModel: MovieViewModel) {
    NavHost(
        navController = navController, // pass in the navController
        startDestination = "home" //sets the starting screen to home
    ) {
        composable("home") {
            HomeScreen(navController, movieViewModel)
        }
        //defines the detailsScreen route with an argument of movieId
        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            //get movieId from arguments
            val movieId = backStackEntry.arguments?.getInt("movieId")
            //find the movie using viewmodel
            movieId?.let {
                val movie = movieViewModel.getMovieById(it)
                if (movie != null) {
                    DetailsScreen(movie = movie)
                }
            }
        }
    }
}
