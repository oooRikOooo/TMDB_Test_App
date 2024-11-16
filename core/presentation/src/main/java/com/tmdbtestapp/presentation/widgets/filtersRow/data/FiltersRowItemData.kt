package com.tmdbtestapp.presentation.widgets.filtersRow.data

import androidx.annotation.StringRes
import com.tmdbtestapp.common.R

sealed class FiltersRowItemData(@StringRes val titleRes: Int) {
    data object Popular: FiltersRowItemData(R.string.popular)
    data object NowPlaying: FiltersRowItemData(R.string.now_playing)
    data object MyFavorites: FiltersRowItemData(R.string.my_favorites)
}