package com.tmdbtestapp.presentation.widgets.movieCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import coil3.compose.rememberAsyncImagePainter
import com.tmdbtestapp.presentation.utils.AppPreviews
import com.tmdbtestapp.presentation.widgets.movieCard.data.MovieCardData

@Composable
fun MovieCard(
    data: MovieCardData,
    onClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(data.imageUrl)

    Card(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .aspectRatio(CardDefaults.VerticalImageAspectRatio),
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(5),
            ),
        ),
        scale = CardDefaults.scale(
            focusedScale = 1.05f,
        )
    ) {
        Image(
            painter = painter,
            contentDescription = null
        )
    }
}

@AppPreviews
@Composable
private fun Preview() {
    val data = MovieCardData(
        id = 0,
        imageUrl = ""
    )
    MovieCard(
        data = data,
        onClick = {}
    )
}