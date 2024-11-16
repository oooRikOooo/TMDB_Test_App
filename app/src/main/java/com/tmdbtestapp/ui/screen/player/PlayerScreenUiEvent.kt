package com.tmdbtestapp.ui.screen.player

sealed class PlayerScreenUiEvent {
    data object Refresh : PlayerScreenUiEvent()
}