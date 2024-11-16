package com.tmdbtestapp.domain.useCase.getPopularMovies

import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    // For future maintenance
    suspend operator fun invoke(): Result<List<Movie>> {
        return movieRepository.getPopularMovies()
    }
}