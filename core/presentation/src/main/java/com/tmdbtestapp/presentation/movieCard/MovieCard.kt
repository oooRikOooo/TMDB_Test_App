package com.tmdbtestapp.presentation.movieCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.tmdbtestapp.presentation.movieCard.data.MovieCardData

@Composable
fun MovieCard(
    data: MovieCardData,
    onClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data.imageUrl)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    )

    Card(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .aspectRatio(CardDefaults.VerticalImageAspectRatio),
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(width = 3.dp, color = Color.Green),
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

@Preview(device = Devices.TV_1080p)
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