package com.tmdbtestapp.presentation.widgets.movieBanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.tmdbtestapp.common.R
import com.tmdbtestapp.presentation.widgets.movieBanner.data.MovieBannerData

@Composable
fun MovieBanner(
    data: MovieBannerData,
    isFavorite: Boolean,
    onTrailerButtonClick: () -> Unit,
    onFavoriteButtonClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data.backgroundImageUrl)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(painter = painter, contentDescription = null)

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            Color.Transparent
                        )
                    )
                )
                .padding(horizontal = 48.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = data.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            DescriptionItemText(
                title = stringResource(R.string.release_date),
                text = data.releaseDate
            )

            DescriptionItemText(
                title = stringResource(R.string.average_vote),
                text = data.voteAverage
            )

            DescriptionItemText(
                title = stringResource(R.string.genres),
                text = data.genres
            )

            Spacer(Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                TrailerButton(onClick = onTrailerButtonClick)

                FavoriteButton(isFavorite = isFavorite, onClick = onFavoriteButtonClick)
            }

        }
    }
}

@Composable
private fun TrailerButton(onClick: () -> Unit) {
    Button(onClick) {
        Text(stringResource(R.string.watch_trailer))
    }
}

@Composable
private fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit) {
    val buttonText by remember(isFavorite) {
        mutableIntStateOf(
            if (isFavorite) R.string.remove_from_favorites else R.string.add_to_favorites
        )
    }

    Button(onClick) {
        Text(stringResource(buttonText))
    }
}

@Composable
private fun DescriptionItemText(
    title: String,
    text: String
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "${title}: ",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}