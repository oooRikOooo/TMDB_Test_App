package com.tmdbtestapp.domain.useCase.getMovieFavoriteState

import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieFavoriteStateUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    // For future maintenance
    operator fun invoke(id: Int): Flow<Boolean> {
        return movieRepository.isMovieFavoriteFlow(id)
    }
}