package com.tmdbtestapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.common.utils.ErrorModel
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.useCase.getMyFavoritesMovies.GetMyFavoritesMoviesUseCase
import com.tmdbtestapp.domain.useCase.getNowPlayingMovies.GetNowPlayingMoviesUseCase
import com.tmdbtestapp.domain.useCase.getPopularMovies.GetPopularMoviesUseCase
import com.tmdbtestapp.presentation.filtersRow.data.FiltersRowItemData
import com.tmdbtestapp.ui.mapper.movieCardData.MovieCardDataMapper
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
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieCardDataMapper: MovieCardDataMapper,
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val getNowPlayingMovies: GetNowPlayingMoviesUseCase,
    private val getMyFavoritesMovies: GetMyFavoritesMoviesUseCase
) : ViewModel() {

    private val isNeedToRefreshFlow = MutableStateFlow(true)
    private val errorFlow = MutableStateFlow<ErrorModel?>(null)

    private val selectedFilterFlow =
        MutableStateFlow<FiltersRowItemData>(FiltersRowItemData.Popular)
    private val filtersFlow = MutableStateFlow(
        listOf(
            FiltersRowItemData.Popular,
            FiltersRowItemData.NowPlaying,
            FiltersRowItemData.MyFavorites
        )
    )

    private val popularMoviesFlow = MutableStateFlow<List<Movie>?>(emptyList())
    private val nowPlayingMoviesFlow = MutableStateFlow<List<Movie>?>(emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dataFlow = isNeedToRefreshFlow.flatMapLatest { isRefreshing ->
        if (isRefreshing) {
            errorFlow.tryEmit(null)
            refresh()
        }

        combine(
            selectedFilterFlow,
            filtersFlow,
            popularMoviesFlow,
            nowPlayingMoviesFlow,
            getMyFavoritesMovies()
        ) { selectedFilter, filters, popularMovies, nowPlayingMovies, myFavoriteMovies ->
            if (popularMovies.isNullOrEmpty() || nowPlayingMovies.isNullOrEmpty()) return@combine null

            val movies = when (selectedFilter) {
                FiltersRowItemData.Popular -> popularMovies
                FiltersRowItemData.NowPlaying -> nowPlayingMovies
                FiltersRowItemData.MyFavorites -> myFavoriteMovies
            }

            val mappedMovies = movies.map { movieCardDataMapper.map(it) }

            HomeScreenState(
                selectedFilter = selectedFilter,
                filtersList = filters,
                movieGridItems = mappedMovies
            )
        }
    }.onEach {
        isNeedToRefreshFlow.tryEmit(false)
    }

    val screenState = combineTransform(
        isNeedToRefreshFlow,
        dataFlow,
        errorFlow
    ) { isNeedToRefresh, data, error ->
        error?.let {
            emit(State.error(it))
            return@combineTransform
        }

        if (isNeedToRefresh) {
            emit(State.loading())
        }

        data?.let {
            emit(State.successes(data))
        }
    }.catch {
        it.printStackTrace()
        emit(State.error(ErrorModel.unknown()))
    }.stateIn(viewModelScope + Dispatchers.IO, SharingStarted.Eagerly, State.loading())

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.OnFilterFocus -> {
                selectedFilterFlow.tryEmit(event.filter)
            }

            HomeScreenUiEvent.Refresh -> {
                isNeedToRefreshFlow.tryEmit(true)
            }
        }
    }

    private suspend fun refresh() {
        fun handleError(errorModel: ErrorModel) {
            errorFlow.tryEmit(errorModel)
        }

        when (val popularMoviesResult = getPopularMovies()) {
            is DataState.Error -> {
                handleError(popularMoviesResult.errorModel)
                return
            }

            is DataState.Success -> {
                popularMoviesFlow.tryEmit(popularMoviesResult.data)
            }
        }

        when (val nowPlayingMoviesResult = getNowPlayingMovies()) {
            is DataState.Error -> {
                handleError(nowPlayingMoviesResult.errorModel)
                return
            }

            is DataState.Success -> {
                nowPlayingMoviesFlow.tryEmit(nowPlayingMoviesResult.data)
            }
        }
    }
}