package com.tmdbtestapp.domain.useCase.toggleMovieFavoriteState

import com.tmdbtestapp.domain.entity.MovieDetails
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleMovieFavoriteStateUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieDetails: MovieDetails) {
        val movie = Movie(
            id = movieDetails.id,
            title = movieDetails.title,
            posterPath = movieDetails.posterPath
        )

        invoke(movie)
    }

    suspend operator fun invoke(movie: Movie) {
        if (movieRepository.isMovieFavoriteFlow(movie.id).first()) {
            movieRepository.removeFromFavorites(movie.id)
        } else {
            movieRepository.addToFavorites(movie)
        }
    }
}