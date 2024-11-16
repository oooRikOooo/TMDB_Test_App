package com.tmdbtestapp.domain.useCase.getMovieDetails

import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    // For future maintenance
    suspend operator fun invoke(id: Int): Result<Movie> {
        return movieRepository.getMovieDetails(id)
    }
}