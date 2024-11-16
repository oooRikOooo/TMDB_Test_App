package com.tmdbtestapp.presentation.widgets.errorContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.tmdbtestapp.common.R

@Composable
fun ErrorContainer(
    onClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        Modifier
            .focusRequester(focusRequester)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center
    ) {
        Button(onClick = onClick) {
            Text(stringResource(R.string.retry))
        }
    }
}