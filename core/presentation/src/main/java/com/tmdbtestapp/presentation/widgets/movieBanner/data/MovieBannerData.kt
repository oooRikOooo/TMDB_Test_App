package com.tmdbtestapp.presentation.widgets.movieBanner.data

data class MovieBannerData(
    val id: Int,
    val title: String,
    val description: String,
    val genres: String,
    val backgroundImageUrl: String,
    val releaseDate: String,
    val voteAverage: String
)