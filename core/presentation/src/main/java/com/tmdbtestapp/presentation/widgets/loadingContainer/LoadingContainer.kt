package com.tmdbtestapp.presentation.widgets.loadingContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.tv.material3.MaterialTheme

@Composable
fun LoadingContainer() {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}