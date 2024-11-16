package com.tmdbtestapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.useCase.getMyFavoritesMovies.GetMyFavoritesMoviesUseCase
import com.tmdbtestapp.domain.useCase.getNowPlayingMovies.GetNowPlayingMoviesUseCase
import com.tmdbtestapp.domain.useCase.getPopularMovies.GetPopularMoviesUseCase
import com.tmdbtestapp.presentation.widgets.filtersRow.data.FiltersRowItemData
import com.tmdbtestapp.ui.screen.home.mapper.MovieCardDataMapper
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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieCardDataMapper: MovieCardDataMapper,
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val getNowPlayingMovies: GetNowPlayingMoviesUseCase,
    getMyFavoritesMovies: GetMyFavoritesMoviesUseCase
) : ViewModel() {
    private val scope = viewModelScope + Dispatchers.IO

    private val isLoadingFlow = MutableStateFlow(false)
    private val errorFlow = MutableStateFlow<Throwable?>(null)

    private val selectedFilterFlow =
        MutableStateFlow<FiltersRowItemData>(FiltersRowItemData.Popular)

    private val filtersFlow = MutableStateFlow(
        listOf(
            FiltersRowItemData.Popular,
            FiltersRowItemData.NowPlaying,
            FiltersRowItemData.MyFavorites
        )
    )

    private val popularMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())
    private val nowPlayingMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())

    private val dataFlow = combine(
        selectedFilterFlow,
        filtersFlow,
        popularMoviesFlow,
        nowPlayingMoviesFlow,
        getMyFavoritesMovies()
    ) { selectedFilter, filters, popularMovies, nowPlayingMovies, myFavoriteMovies ->
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

    val screenState = combineTransform(
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

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.OnFilterFocus -> {
                selectedFilterFlow.tryEmit(event.filter)
            }

            HomeScreenUiEvent.Refresh -> {
                scope.launch { load() }
            }
        }
    }

    private suspend fun load() {
        isLoadingFlow.tryEmit(true)
        errorFlow.tryEmit(null)

        fun handleError(e: Throwable) {
            errorFlow.tryEmit(e)
        }

        getPopularMovies().onSuccess {
            popularMoviesFlow.tryEmit(it)
        }.onFailure {
            handleError(it)
            return
        }

        getNowPlayingMovies().onSuccess {
            nowPlayingMoviesFlow.tryEmit(it)
        }.onFailure {
            handleError(it)
            return
        }

        isLoadingFlow.tryEmit(false)
    }
}