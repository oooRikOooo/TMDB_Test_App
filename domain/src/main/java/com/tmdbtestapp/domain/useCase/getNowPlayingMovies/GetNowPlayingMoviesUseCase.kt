package com.tmdbtestapp.domain.useCase.getNowPlayingMovies

import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    // For future maintenance
    suspend operator fun invoke(): Result<List<Movie>> {
        return movieRepository.getNowPlayingMovies()
    }
}