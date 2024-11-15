package com.tmdbtestapp.ui.mapper.movieBannerData

import com.tmdbtestapp.common.manager.ImageUrlManager
import com.tmdbtestapp.domain.entity.MovieDetails
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.presentation.movieBanner.data.MovieBannerData
import javax.inject.Inject

class MovieBannerDataMapper @Inject constructor(
    private val imageUrlManager: ImageUrlManager
) {
    fun map(movie: MovieDetails): MovieBannerData {
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