package com.tmdbtestapp.ui.screen.player

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.domain.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel(assistedFactory = PlayerViewModel.PlayerViewModelFactory::class)
class PlayerViewModel @AssistedInject constructor(
    @Assisted val movieId: Int,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val scope = viewModelScope + Dispatchers.IO

    private val isLoadingFlow = MutableStateFlow(false)
    private val videoUriFlow = MutableStateFlow<Uri>(Uri.EMPTY)
    private val errorFlow = MutableStateFlow<Throwable?>(null)

    val screenState = combineTransform(
        isLoadingFlow,
        videoUriFlow,
        errorFlow
    ) { isLoading, videoUri, error ->
        error?.let {
            emit(State.error(it))
            return@combineTransform
        }

        if (isLoading) {
            emit(State.loading())
            return@combineTransform
        }

        emit(State.successes(PlayerScreenState(videoUri)))
    }.onStart {
        load()
    }.catch {
        it.printStackTrace()
        emit(State.error(it))
    }.stateIn(scope, SharingStarted.Eagerly, State.loading())

    fun onEvent(event: PlayerScreenUiEvent) {
        when (event) {
            PlayerScreenUiEvent.Refresh -> {
                scope.launch { load() }
            }
        }
    }

    private suspend fun load() {
        isLoadingFlow.tryEmit(true)

        movieRepository.getVideoUrl(movieId).onSuccess {
            val uri = Uri.parse(it)

            videoUriFlow.tryEmit(uri)
        }.onFailure {
            errorFlow.tryEmit(it)
        }

        isLoadingFlow.tryEmit(false)
    }

    @AssistedFactory
    interface PlayerViewModelFactory {
        fun create(movieId: Int): PlayerViewModel
    }
}