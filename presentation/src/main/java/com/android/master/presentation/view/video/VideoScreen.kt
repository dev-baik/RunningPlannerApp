package com.android.master.presentation.view.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.android.master.domain.model.VideoSearchItem
import com.android.master.presentation.R
import com.android.master.presentation.intent.VideoIntent
import com.android.master.presentation.state.VideoViewState
import com.android.master.presentation.utils.isNetworkAvailable
import com.android.master.presentation.view.NavigationItem.MainNav.Home
import com.android.master.presentation.view.theme.RunningPlannerAppTheme
import com.android.master.presentation.viewmodel.VideoViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun SearchTextFieldPreview() {
    RunningPlannerAppTheme {
        SearchTextField("") { }
    }
}

@Preview(showBackground = true)
@Composable
fun RetryBoxPreview() {
    RunningPlannerAppTheme {
        RetryBox { }
    }
}

@Composable
fun VideoScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<VideoViewModel>()
    val scope = rememberCoroutineScope()
    val videoListState = rememberLazyStaggeredGridState()

    var searchText by remember { mutableStateOf("") }
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    val isPrevPageAvailable by rememberUpdatedState(
        (viewState as? VideoViewState.Success)?.isPrevPageAvailable ?: false
    )

    videoListState.OnBottomReached(isPrevPageAvailable) {
        viewModel.sendIntent(VideoIntent.LoadNextPage)
    }

    Column {
        SearchTextField(searchText) {
            viewModel.sendIntent(VideoIntent.UpdateKeyword(it))
            scope.launch { videoListState.scrollToItem(0) }
            searchText = it
        }

        Spacer(Modifier.size(8.dp))

        when (viewState) {
            is VideoViewState.Loading -> {
                val loadingState = viewState as VideoViewState.Loading
                if (loadingState.previousItems.isNotEmpty()) {
                    VideoVerticalGrid(
                        videoListState = videoListState,
                        videoItem = VideoSearchItem(loadingState.previousItems)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(35.dp))
                }
            }

            is VideoViewState.Success -> {
                val successState = viewState as VideoViewState.Success
                VideoVerticalGrid(
                    videoListState = videoListState,
                    videoItem = VideoSearchItem(successState.videoItems)
                )
            }

            is VideoViewState.NotFound -> {
                val notFoundState = viewState as VideoViewState.NotFound
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(stringResource(R.string.video_search_not_found, notFoundState.keyword))
                }
            }

            is VideoViewState.Error -> {
                if (LocalContext.current.isNetworkAvailable()) {
                    RetryBox { viewModel.sendIntent(VideoIntent.Retry) }
                } else {
                    navController.popBackStack(Home.route, false)
                }
            }

            is VideoViewState.Idle -> {}
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ColumnScope.VideoVerticalGrid(
    videoListState: LazyStaggeredGridState,
    videoItem: VideoSearchItem,
) {
    val deviceWidth = LocalContext.current.resources.displayMetrics.widthPixels

    LazyVerticalStaggeredGrid(
        modifier = Modifier.weight(1f),
        state = videoListState,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 1.dp,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(
            items = videoItem.videoItemList,
            key = { it.id }
        ) {
            GlideImage(
                model = it.videos.tiny.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            ) { requestBuilder ->
                requestBuilder
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .override(deviceWidth / 2, it.videos.tiny.height)
                    .encodeQuality(50)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            }
        }
    }
}

@Composable
fun RetryBox(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.network_connect_error),
            textAlign = TextAlign.Center
        )

        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun SearchTextField(
    prevText: String,
    submit: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf(prevText) }

    Row {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                submit(text)
                keyboardController?.hide()
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}

@Composable
fun LazyStaggeredGridState.OnBottomReached(
    isPrevPageAvailable: Boolean,
    onLoadMore: () -> Unit,
) {
    LaunchedEffect(this, isPrevPageAvailable) {
        snapshotFlow { layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
            .collectLatest { lastIndex ->
                if (isPrevPageAvailable && lastIndex >= layoutInfo.totalItemsCount - 1) {
                    onLoadMore()
                }
            }
    }
}