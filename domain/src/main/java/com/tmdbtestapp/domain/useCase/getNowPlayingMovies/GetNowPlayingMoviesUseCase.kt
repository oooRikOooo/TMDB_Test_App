package com.tmdbtestapp.domain.useCase.getNowPlayingMovies

import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class)
class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): DataState<List<Movie>> {
        return movieRepository.getNowPlayingMovies()
    }
}