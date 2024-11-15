package com.tmdbtestapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.Text
import com.tmdbtestapp.common.navigation.NavRoute
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.presentation.errorContainer.ErrorContainer
import com.tmdbtestapp.presentation.filtersRow.FiltersRow
import com.tmdbtestapp.presentation.loadingContainer.LoadingContainer
import com.tmdbtestapp.presentation.movieCard.MovieCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateTo: (NavRoute) -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    when (val state = screenState) {
        is State.Error -> {
            ErrorContainer {
                viewModel.onEvent(HomeScreenUiEvent.Refresh)
            }
        }

        is State.Loading -> {
            LoadingContainer()
        }

        is State.Successes -> {
            Content(
                data = state.data,
                onEvent = viewModel::onEvent,
                navigateTo = navigateTo
            )
        }
    }
}

@Composable
private fun Content(
    data: HomeScreenState,
    onEvent: (HomeScreenUiEvent) -> Unit,
    navigateTo: (NavRoute) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxSize()
            .background(Color.Black)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FiltersRow(
            selectedFilter = data.selectedFilter,
            filtersList = data.filtersList,
            onFilterFocus = { onEvent(HomeScreenUiEvent.OnFilterFocus(it)) }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(
                items = data.movieGridItems,
                key = { it.id }
            ) { movieItem ->
                MovieCard(
                    data = movieItem,
                    onClick = { navigateTo(NavRoute.DetailNavRoute(movieItem.id)) }
                )
            }
        }
    }
}