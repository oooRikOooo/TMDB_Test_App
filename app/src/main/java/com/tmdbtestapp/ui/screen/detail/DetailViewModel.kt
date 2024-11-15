package com.tmdbtestapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.common.utils.ErrorModel
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.domain.entity.MovieDetails
import com.tmdbtestapp.domain.useCase.getMovieDetails.GetMovieDetailsUseCase
import com.tmdbtestapp.domain.useCase.getMovieFavoriteState.GetMovieFavoriteStateUseCase
import com.tmdbtestapp.domain.useCase.toggleMovieFavoriteState.ToggleMovieFavoriteStateUseCase
import com.tmdbtestapp.presentation.movieBanner.data.MovieBannerData
import com.tmdbtestapp.ui.mapper.movieBannerData.MovieBannerDataMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel(assistedFactory = DetailViewModel.DetailViewModelFactory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val movieBannerDataMapper: MovieBannerDataMapper,
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val getMovieFavoriteState: GetMovieFavoriteStateUseCase,
    private val toggleMovieFavoriteState: ToggleMovieFavoriteStateUseCase
) : ViewModel() {

    private val isNeedToRefreshFlow = MutableStateFlow(true)
    private val errorFlow = MutableStateFlow<ErrorModel?>(null)

    private val movieDetailsFlow = MutableStateFlow<MovieDetails?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dataFlow = isNeedToRefreshFlow.flatMapLatest { isRefreshing ->

        if (isRefreshing) {
            errorFlow.tryEmit(null)
            refresh()
        }

        combine(
            movieDetailsFlow,
            getMovieFavoriteState(id)
        ) { movieDetail, isFavorite ->
            movieDetail ?: return@combine null

            val mappedMovieDetails = movieBannerDataMapper.map(movieDetail)
            DetailScreenState(
                movie = mappedMovieDetails,
                isFavorite = isFavorite
            )
        }.onEach {
            isNeedToRefreshFlow.tryEmit(false)
        }
    }

    val screenData = combineTransform(
        isNeedToRefreshFlow,
        dataFlow,
        errorFlow
    ) { isNeedToRefresh, data, error ->
        error?.let {
            emit(State.error(it))
            return@combineTransform
        }

        if (isNeedToRefresh) emit(State.loading())

        data?.let {
            emit(State.successes(it))
        }
    }.catch {
        it.printStackTrace()
        emit(State.error(ErrorModel.unknown()))
    }.stateIn(viewModelScope + Dispatchers.IO, SharingStarted.Eagerly, State.loading())

    fun onEvent(event: DetailScreenUiEvent) {
        when (event) {
            DetailScreenUiEvent.ToggleFavoriteState -> {
                toggleFavoriteState()
            }

            DetailScreenUiEvent.Refresh -> {
                isNeedToRefreshFlow.tryEmit(true)
            }
        }
    }

    private suspend fun refresh() {
        when (val result = getMovieDetails(id)) {
            is DataState.Error -> {
                errorFlow.tryEmit(result.errorModel)
            }

            is DataState.Success -> {
                movieDetailsFlow.tryEmit(result.data)
            }
        }
    }

    private fun toggleFavoriteState() {
        viewModelScope.launch {
            movieDetailsFlow.value?.let {
                toggleMovieFavoriteState(it)
            }
        }
    }

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(movieId: Int): DetailViewModel
    }
}