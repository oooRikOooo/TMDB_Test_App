package com.tmdbtestapp.ui.mapper.movieCardData

import com.tmdbtestapp.common.manager.ImageSize
import com.tmdbtestapp.common.manager.ImageUrlManager
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.presentation.movieCard.data.MovieCardData
import javax.inject.Inject

class MovieCardDataMapper @Inject constructor(
    private val imageUrlManager: ImageUrlManager
) {
    fun map(movie: Movie): MovieCardData {
        return MovieCardData(
            id = movie.id,
            imageUrl = imageUrlManager.getUrl(imagePath = movie.posterPath, size = ImageSize.W342),
        )
    }
}