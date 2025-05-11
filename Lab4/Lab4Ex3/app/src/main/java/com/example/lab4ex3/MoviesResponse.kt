package com.example.lab4ex3

data class Movie(
    val title: String,
    val overview: String,
    val poster_path: String
)
data class MovieResponse(
    val results: List<Movie>
)
