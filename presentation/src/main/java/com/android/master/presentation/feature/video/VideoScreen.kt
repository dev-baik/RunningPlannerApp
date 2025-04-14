package com.android.master.presentation.feature.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.master.domain.exception.EmptyListException
import com.android.master.domain.exception.RPAppException
import com.android.master.presentation.R
import com.android.master.presentation.feature.video.component.ReceivedVideoThumbnail
import com.android.master.presentation.feature.video.model.VideoUiModel
import com.android.master.presentation.ui.component.dialog.RPAppVideoDialog
import com.android.master.presentation.ui.component.textfield.RPAppBasicTextField
import com.android.master.presentation.ui.theme.RPAppTheme
import com.android.master.presentation.util.view.LoadState
import androidx.paging.LoadState as PagingLoadState

@Composable
fun VideoRoute(
    viewModel: VideoViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackbar: (String, SnackbarDuration) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagedReceivedVideo = viewModel.receivedVideos.collectAsLazyPagingItems()

    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    exoPlayer.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)

            if (playbackState == Player.STATE_ENDED) {
                viewModel.setEvent(VideoContract.VideoEvent.DismissDialogVideo)
            }
        }
    })

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { videoSideEffect ->
                when (videoSideEffect) {
                    is VideoContract.VideoSideEffect.ShowSnackbar -> onShowSnackbar(videoSideEffect.message, videoSideEffect.duration)
                }
            }
    }

    LaunchedEffect(uiState.isVideoDialogOpen) {
        if (uiState.isVideoDialogOpen) {
            val mediaItem = MediaItem.fromUri(uiState.url)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        } else {
            exoPlayer.stop()
            exoPlayer.clearMediaItems()
        }
    }

    LaunchedEffect(pagedReceivedVideo.loadState) {
        when (pagedReceivedVideo.loadState.refresh) {
            is PagingLoadState.Loading -> { viewModel.setEvent(VideoContract.VideoEvent.FetchVideos(videoLoadState = LoadState.Loading)) }
            is PagingLoadState.NotLoading -> { viewModel.setEvent(VideoContract.VideoEvent.FetchVideos(videoLoadState = LoadState.Success)) }
            is PagingLoadState.Error -> {
                val e = pagedReceivedVideo.loadState.refresh as PagingLoadState.Error
                when (e.error) {
                    is RPAppException -> {
                        e.error.message?.let {
                            viewModel.setSideEffect(VideoContract.VideoSideEffect.ShowSnackbar(message = it))
                        }
                        viewModel.setEvent(VideoContract.VideoEvent.FetchVideos(videoLoadState = LoadState.Error))
                    }

                    is EmptyListException -> { viewModel.setEvent(VideoContract.VideoEvent.FetchVideos(videoLoadState = LoadState.Idle)) }
                }
            }
        }
    }

    when (uiState.loadState) {
        LoadState.Idle -> {
            VideoScreen(
                padding = padding,
                exoPlayer = exoPlayer,
                videoUiState = uiState,
                pagedReceivedSignal = pagedReceivedVideo,
                onSearchVideo = { viewModel.fetchVideo() },
                onDescriptionValueChange = { keyword -> viewModel.setEvent(VideoContract.VideoEvent.OnKeywordValueChange(keyword = keyword)) },
                onImageButtonClick = { url, height -> viewModel.setEvent(VideoContract.VideoEvent.OnDialogVideo(url = url, height = height)) },
                dismissDialogVideo = { viewModel.setEvent(VideoContract.VideoEvent.DismissDialogVideo) }
            )
        }

        LoadState.Success -> {
            // TODO 운동 시작
        }

        else -> Unit
    }
}

@Composable
fun VideoScreen(
    padding: PaddingValues,
    videoUiState: VideoContract.VideoUiState = VideoContract.VideoUiState(),
    pagedReceivedSignal: LazyPagingItems<VideoUiModel>,
    exoPlayer: ExoPlayer,
    onDescriptionValueChange: (String) -> Unit,
    onSearchVideo: () -> Unit,
    onImageButtonClick: (String, Int) -> Unit,
    dismissDialogVideo: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        RPAppBasicTextField(
            icon = Icons.Filled.Search to stringResource(R.string.video_search_icon_description),
            value = videoUiState.keyword,
            onValueChange = onDescriptionValueChange,
            placeholder = stringResource(id = R.string.video_search_term_placeholder),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchVideo()
                keyboardController?.hide()
            }),
        )
        ReceivedVideoThumbnail(
            loadState = videoUiState.videoLoadState,
            pagedReceivedSignal = pagedReceivedSignal,
            onImageButtonClick = onImageButtonClick,
        )
    }

    if (videoUiState.isVideoDialogOpen) {
        RPAppVideoDialog(
            exoPlayer = exoPlayer,
            height = videoUiState.height.dp / 2,
            borderColor = RPAppTheme.colors.black,
            borderWidth = 3.dp,
            onDismissRequest = dismissDialogVideo
        )
    }
}