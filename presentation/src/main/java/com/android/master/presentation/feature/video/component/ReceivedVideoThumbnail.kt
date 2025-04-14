package com.android.master.presentation.feature.video.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.android.master.presentation.R
import com.android.master.presentation.feature.video.model.VideoUiModel
import com.android.master.presentation.ui.component.view.RPAppLoadingView
import com.android.master.presentation.util.view.LoadState
import com.android.master.presentation.util.view.LoadState.Error
import com.android.master.presentation.util.view.LoadState.Idle
import com.android.master.presentation.util.view.LoadState.Loading
import com.android.master.presentation.util.view.LoadState.Success
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReceivedVideoThumbnail(
    modifier: Modifier = Modifier,
    loadState: LoadState,
    pagedReceivedSignal: LazyPagingItems<VideoUiModel>,
    onImageButtonClick: (String, Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val deviceWidth = remember {
        with(density) { configuration.screenWidthDp.dp.toPx() }.toInt()
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        when (loadState) {
            Idle -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    EmptyView()
                }
            }

            Loading -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    RPAppLoadingView()
                }
            }

            Success -> {
                items(
                    count = pagedReceivedSignal.itemCount,
                    key = pagedReceivedSignal.itemKey(VideoUiModel::id),
                    contentType = pagedReceivedSignal.itemContentType { "ReceivedVideo" }
                ) { index ->
                    val receivedVideo = pagedReceivedSignal[index]
                    receivedVideo?.let {
                        GlideImage(
                            model = it.thumbnail,
                            contentDescription = stringResource(R.string.video_item_description),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clickable { onImageButtonClick(it.url, it.height) }
                        ) { requestBuilder ->
                            requestBuilder
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .override(deviceWidth / 2, it.height)
                                .encodeQuality(50)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        }
                    }
                }
            }

            Error -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ErrorView()
                }
            }
        }
    }
}

@Composable
private fun EmptyView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.no_search_result))
    }
}

@Composable
private fun ErrorView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.error_server))
    }
}