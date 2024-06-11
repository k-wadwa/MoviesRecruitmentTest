package com.example.movieviewer

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("837b93dbd48250d44bae")
    fun getMovies(): Call<List<Movie>>
}
