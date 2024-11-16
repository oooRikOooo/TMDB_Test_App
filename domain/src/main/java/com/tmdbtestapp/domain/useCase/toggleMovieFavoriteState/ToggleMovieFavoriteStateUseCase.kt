package com.tmdbtestapp.domain.useCase.toggleMovieFavoriteState

import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleMovieFavoriteStateUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        val isMovieFavorite = movieRepository.isMovieFavoriteFlow(movie.id).first()

        if (isMovieFavorite) {
            movieRepository.removeFromFavorites(movie.id)
        } else {
            movieRepository.addToFavorites(movie)
        }
    }
}