package com.example.movieviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//a viewmodel manages state and data for the movie app
class MovieViewModel(private val apiService: ApiService) : ViewModel() {

    //LiveData to hold list of movies
    var _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    //LiveData to hold list of filtered movies based on search query
    private var _filteredMovies = mutableStateOf<List<Movie>>(emptyList())
    val filteredMovies: State<List<Movie>> = _filteredMovies

    //MutableState for the search query
    var searchQuery by mutableStateOf("")
        private set

    //MutableState to indicate loading status
    var isLoading by mutableStateOf(true)
        private set

    //MutableState to hold error messages
    var errorMessage by mutableStateOf("")
        private set

    //initialise the viewmodel by fetching movies
    init {
        fetchMovies()
    }

    //function that fetches movies from the api
    fun fetchMovies() {
        viewModelScope.launch {
            //access the singleton retroFit instance to create an implementation of ApiService interface.
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            //make asynchronous network request, returns object defined in ApiService interface (Call<List<Movie>>)
            apiService.getMovies().enqueue(object : Callback<List<Movie>> {
                override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                    //checks if status code is within 200-299 range.
                    if (response.isSuccessful) {
                        //maps each movie object to a movie object with an ID
                        val movies = response.body()?.mapIndexed { index, movie ->
                            movie.copy(id = index) //assign unique ID based on its index
                        } ?: emptyList()
                        //updates _movies.value with list of movies, same for filteredMovies
                        _movies.value = movies
                        _filteredMovies.value = _movies.value
                        isLoading = false
                    } else {
                        errorMessage = "Error: ${response.message()}"
                        isLoading = false
                    }
                }
                //display error message in event of failure.
                override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                    errorMessage = "Failure: ${t.message}"
                    isLoading = false
                }
            })
        }
    }

    //function that handles live changes in search query
    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        _filteredMovies.value = if (query.isEmpty()) {
            _movies.value
        } else {
            _movies.value.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.genre.contains(query, ignoreCase = true)
            }
        }
    }
    fun getMovieById(id: Int): Movie? {
        return _movies.value.find { it.id == id }
    }
}
