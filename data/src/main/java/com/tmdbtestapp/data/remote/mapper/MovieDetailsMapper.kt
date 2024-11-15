package com.tmdbtestapp.data.remote.mapper

import com.tmdbtestapp.data.remote.entity.movieDetails.MovieDetailsDto
import com.tmdbtestapp.domain.entity.MovieDetails
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() {
    fun map(dto: MovieDetailsDto?): MovieDetails? {
        dto ?: return null

        return MovieDetails(
            id = dto.id ?: return null,
            title = dto.title.orEmpty(),
            description = dto.overview.orEmpty(),
            genres = dto.genres?.mapNotNull { it.name } ?: emptyList(),
            backdropPath = dto.backdropPath.orEmpty(),
            posterPath = dto.posterPath.orEmpty(),
            releaseDate = dto.releaseDate.orEmpty(),
            voteAverage = dto.voteAverage.toString()
        )
    }
}