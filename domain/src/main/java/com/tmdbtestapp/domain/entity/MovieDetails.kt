package com.tmdbtestapp.domain.entity

data class MovieDetails(
    val id: Int,
    val title: String,
    val description: String,
    val genres: List<String>,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: String
)
