package com.example.movieviewer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Awards") val awards: String,
    @SerializedName("Poster") val posterUrl: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Country") val country: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Metascore") val metascore: String,
    @SerializedName("imdbVotes") val imdbVotes: String,
    @SerializedName("imdbRating") val imdbRating: String
) : Parcelable
