package com.tmdbtestapp.presentation.widgets.filtersRow

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.TabRowScope
import androidx.tv.material3.Text
import com.tmdbtestapp.presentation.utils.AppPreviews
import com.tmdbtestapp.presentation.widgets.filtersRow.data.FiltersRowItemData

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FiltersRow(
    selectedFilter: FiltersRowItemData,
    filtersList: List<FiltersRowItemData>,
    modifier: Modifier = Modifier,
    onFilterFocus: (FiltersRowItemData) -> Unit
) {
    val selectedFilterIndex by remember(selectedFilter) {
        mutableIntStateOf(filtersList.indexOf(selectedFilter))
    }

    TabRow(
        selectedTabIndex = selectedFilterIndex,
        modifier = modifier.focusRestorer()
    ) {
        filtersList.forEachIndexed { index, filter ->
            Filter(
                selected = selectedFilterIndex == index,
                filter = filter,
                onFilterFocus = { onFilterFocus(filter) }
            )
        }
    }
}

@Composable
private fun TabRowScope.Filter(
    selected: Boolean,
    filter: FiltersRowItemData,
    onFilterFocus: () -> Unit
) {
    Tab(
        selected = selected,
        onFocus = onFilterFocus,
    ) {
        Text(
            text = stringResource(filter.titleRes),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@AppPreviews
@Composable
private fun Preview() {
    val filtersList = listOf(
        FiltersRowItemData.Popular,
        FiltersRowItemData.NowPlaying,
        FiltersRowItemData.MyFavorites,
    )

    var selectedFilter by remember { mutableStateOf(filtersList[0]) }

    FiltersRow(
        selectedFilter = selectedFilter,
        filtersList = filtersList,
        onFilterFocus = { selectedFilter = it }
    )
}
