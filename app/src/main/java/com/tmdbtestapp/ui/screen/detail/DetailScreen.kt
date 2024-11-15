package com.tmdbtestapp.ui.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tmdbtestapp.common.navigation.NavRoute
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.presentation.errorContainer.ErrorContainer
import com.tmdbtestapp.presentation.loadingContainer.LoadingContainer
import com.tmdbtestapp.presentation.movieBanner.MovieBanner

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navigateTo: (NavRoute) -> Unit
) {
    val screenState by viewModel.screenData.collectAsStateWithLifecycle()

    when (val state = screenState) {
        is State.Error -> {
            ErrorContainer {
                viewModel.onEvent(DetailScreenUiEvent.Refresh)
            }
        }

        is State.Loading -> {
            LoadingContainer()
        }

        is State.Successes -> {
            Content(data = state.data, onEvent = viewModel::onEvent, navigateTo = navigateTo)
        }

    }
}

@Composable
private fun Content(
    data: DetailScreenState,
    onEvent: (DetailScreenUiEvent) -> Unit,
    navigateTo: (NavRoute) -> Unit
) {
    MovieBanner(
        data = data.movie,
        isFavorite = data.isFavorite,
        onTrailerButtonClick = { navigateTo(NavRoute.PlayerNavRoute) },
        onFavoriteButtonClick = { onEvent(DetailScreenUiEvent.ToggleFavoriteState) }
    )
}