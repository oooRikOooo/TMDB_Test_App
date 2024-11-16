package com.tmdbtestapp.ui.screen.home

import com.tmdbtestapp.presentation.widgets.filtersRow.data.FiltersRowItemData
import com.tmdbtestapp.presentation.widgets.movieCard.data.MovieCardData

data class HomeScreenState(
    val selectedFilter: FiltersRowItemData,
    val filtersList: List<FiltersRowItemData>,
    val movieGridItems: List<MovieCardData>
)
