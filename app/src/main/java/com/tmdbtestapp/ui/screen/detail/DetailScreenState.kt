package com.tmdbtestapp.ui.screen.detail

import com.tmdbtestapp.presentation.movieBanner.data.MovieBannerData

data class DetailScreenState(
    val movie: MovieBannerData,
    val isFavorite: Boolean
)