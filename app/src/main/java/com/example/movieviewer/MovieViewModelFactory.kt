package com.example.movieviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
the ViewModelFactory is used to provide dependencies to the Viewmodel.
this is because the MovieViewModel requires an instance of ApiService.
The default ViewModelProvider does not know how to create ApiService and pass it to MovieViewModel without this
*/

class MovieViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
