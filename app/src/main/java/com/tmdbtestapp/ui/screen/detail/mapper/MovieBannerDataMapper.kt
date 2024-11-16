package com.tmdbtestapp.ui.screen.detail.mapper

import com.tmdbtestapp.common.manager.ImageUrlManager
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.presentation.widgets.movieBanner.data.MovieBannerData
import javax.inject.Inject

class MovieBannerDataMapper @Inject constructor(
    private val imageUrlManager: ImageUrlManager
) {
    fun map(movie: Movie): MovieBannerData {
        return MovieBannerData(
            id = movie.id,
            title = movie.title,
            description = movie.description,
            genres = movie.genres.joinToString(", "),
            backgroundImageUrl = imageUrlManager.getUrl(movie.backdropPath),
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage
        )
    }
}