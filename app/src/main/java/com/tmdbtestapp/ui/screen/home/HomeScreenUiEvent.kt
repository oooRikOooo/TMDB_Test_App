package com.tmdbtestapp.ui.screen.home

import com.tmdbtestapp.presentation.widgets.filtersRow.data.FiltersRowItemData

sealed class HomeScreenUiEvent {
    data class OnFilterFocus(val filter: FiltersRowItemData): HomeScreenUiEvent()
    data object Refresh: HomeScreenUiEvent()
}