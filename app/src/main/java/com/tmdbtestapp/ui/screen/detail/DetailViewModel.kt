package com.tmdbtestapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.useCase.getMovieDetails.GetMovieDetailsUseCase
import com.tmdbtestapp.domain.useCase.getMovieFavoriteState.GetMovieFavoriteStateUseCase
import com.tmdbtestapp.domain.useCase.toggleMovieFavoriteState.ToggleMovieFavoriteStateUseCase
import com.tmdbtestapp.ui.screen.detail.mapper.MovieBannerDataMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel(assistedFactory = DetailViewModel.DetailViewModelFactory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val movieBannerDataMapper: MovieBannerDataMapper,
    private val getMovieDetails: GetMovieDetailsUseCase,
    getMovieFavoriteState: GetMovieFavoriteStateUseCase,
    private val toggleMovieFavoriteState: ToggleMovieFavoriteStateUseCase
) : ViewModel() {
    private val scope = viewModelScope + Dispatchers.IO

    private val isLoadingFlow = MutableStateFlow(false)
    private val errorFlow = MutableStateFlow<Throwable?>(null)

    private val movieDetailsFlow = MutableStateFlow(Movie.empty())

    private val dataFlow = combine(
        movieDetailsFlow,
        getMovieFavoriteState(id)
    ) { movieDetail, isFavorite ->
        val mappedMovieDetails = movieBannerDataMapper.map(movieDetail)

        DetailScreenState(
            movie = mappedMovieDetails,
            isFavorite = isFavorite
        )
    }

    val screenData = combineTransform(
        isLoadingFlow,
        dataFlow,
        errorFlow
    ) { isNeedToRefresh, data, error ->
        error?.let {
            emit(State.error(it))
            return@combineTransform
        }

        if (isNeedToRefresh) {
            emit(State.loading())
            return@combineTransform
        }

        emit(State.successes(data))
    }.onStart {
        load()
    }.catch {
        it.printStackTrace()
        emit(State.error())
    }.stateIn(scope, SharingStarted.Eagerly, State.loading())

    fun onEvent(event: DetailScreenUiEvent) {
        when (event) {
            DetailScreenUiEvent.ToggleFavoriteState -> {
                toggleFavoriteState()
            }

            DetailScreenUiEvent.Refresh -> {
                scope.launch { load() }
            }
        }
    }

    private suspend fun load() {
        isLoadingFlow.tryEmit(true)
        errorFlow.tryEmit(null)

        getMovieDetails(id).onSuccess {
            movieDetailsFlow.tryEmit(it)
        }.onFailure {
            errorFlow.tryEmit(it)
        }

        isLoadingFlow.tryEmit(false)
    }

    private fun toggleFavoriteState() {
        viewModelScope.launch {
            toggleMovieFavoriteState(movieDetailsFlow.value)
        }
    }

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(movieId: Int): DetailViewModel
    }
}