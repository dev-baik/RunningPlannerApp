package com.android.master.presentation.ui.component.dialog

import androidx.annotation.OptIn
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun RPAppVideoDialog(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer,
    height: Dp = 0.dp,
    borderColor: Color = Color.Unspecified,
    borderWidth: Dp = 0.dp,
    cornerRadius: Dp = 0.dp,
    onDismissRequest: () -> Unit = {}
) {
    RPAppDialog(
        onDismissRequest = onDismissRequest,
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                    useController = false
                }
            },
            modifier = modifier
                .height(height = height)
                .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(cornerRadius))
        )
    }
}