package com.tmdbtestapp.domain.entity.movie

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val description: String = "",
    val genres: List<String> = emptyList(),
    val backdropPath: String = "",
    val releaseDate: String = "",
    val voteAverage: String = ""
) {
    companion object {
        fun empty() = Movie(
            id = 0,
            title = "",
            posterPath = ""
        )
    }
}
