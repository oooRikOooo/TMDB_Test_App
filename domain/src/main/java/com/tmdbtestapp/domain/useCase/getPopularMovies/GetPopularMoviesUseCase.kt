package com.tmdbtestapp.domain.useCase.getPopularMovies

import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(FlowPreview::class)
class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): DataState<List<Movie>> {
        return movieRepository.getPopularMovies()
    }
}