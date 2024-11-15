package com.tmdbtestapp.ui.screen.detail

sealed class DetailScreenUiEvent {
    data object ToggleFavoriteState: DetailScreenUiEvent()
    data object Refresh: DetailScreenUiEvent()
}