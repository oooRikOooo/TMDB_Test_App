package com.tmdbtestapp.ui.screen.player

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import androidx.tv.material3.MaterialTheme
import com.tmdbtestapp.common.utils.State
import com.tmdbtestapp.presentation.utils.ComposableLifecycle
import com.tmdbtestapp.presentation.widgets.errorContainer.ErrorContainer
import com.tmdbtestapp.presentation.widgets.loadingContainer.LoadingContainer

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    when (val state = screenState) {
        is State.Error -> {
            ErrorContainer {
                viewModel.onEvent(PlayerScreenUiEvent.Refresh)
            }
        }

        is State.Loading -> {
            LoadingContainer()
        }

        is State.Successes -> {
            Content(
                data = state.data
            )
        }
    }
}

@Composable
private fun Content(
    data: PlayerScreenState
) {
    val context = LocalContext.current

    var player: ExoPlayer? by remember {
        mutableStateOf(null)
    }

    val playerView = createPlayerView(player)

    ComposableLifecycle(
        onStart = {
            player = initPlayer(context, data.videoUri)
            playerView.onResume()
        },
        onStop = {
            playerView.apply {
                player?.release()
                onPause()
                player = null
            }
        }
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .focusProperties { canFocus = false },
            factory = { playerView }
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun createPlayerView(player: Player?): PlayerView {
    val context = LocalContext.current

    val playerView = remember {
        PlayerView(context).apply {
            this.player = player
            controllerAutoShow = true
            useController = true
            keepScreenOn = true
        }
    }

    DisposableEffect(player) {
        playerView.player = player

        onDispose {
            playerView.player = null
        }
    }

    return playerView
}

@OptIn(UnstableApi::class)
fun initPlayer(context: Context, videoUri: Uri): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()

        val mediaSource = buildMediaSource(
            videoUri,
            defaultHttpDataSourceFactory,
            null
        )

        setMediaSource(mediaSource)
        playWhenReady = true
        prepare()
    }
}

@OptIn(UnstableApi::class)
fun buildMediaSource(
    uri: Uri,
    defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory,
    overrideExtension: String?,
): MediaSource {
    val type = if (overrideExtension.isNullOrEmpty()) {
        Util.inferContentType(uri)
    } else {
        Util.inferContentTypeForExtension(overrideExtension)
    }
    return when (type) {
        C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        else -> {
            throw IllegalStateException("Unsupported type: $type")
        }
    }
}