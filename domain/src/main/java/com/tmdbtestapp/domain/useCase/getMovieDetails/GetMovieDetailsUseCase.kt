package com.tmdbtestapp.domain.useCase.getMovieDetails

import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.domain.entity.MovieDetails
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class)
class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): DataState<MovieDetails> {
        return movieRepository.getMovieDetails(id)
    }
}