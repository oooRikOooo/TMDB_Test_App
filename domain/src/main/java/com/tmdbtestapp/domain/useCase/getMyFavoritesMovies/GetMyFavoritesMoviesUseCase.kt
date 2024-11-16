package com.tmdbtestapp.domain.useCase.getMyFavoritesMovies

import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyFavoritesMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    // For future maintenance
    operator fun invoke(): Flow<List<Movie>> {
        return movieRepository.getFavoriteMovies()
    }
}