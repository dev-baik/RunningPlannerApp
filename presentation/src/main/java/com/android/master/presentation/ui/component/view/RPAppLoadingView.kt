package com.android.master.presentation.ui.component.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.master.presentation.LoadingView.CLIP_MAX
import com.android.master.presentation.LoadingView.CLIP_MIN
import com.android.master.presentation.LoadingView.LOTTIE
import com.android.master.presentation.ui.theme.RPAPPTheme

@Preview
@Composable
fun RPAppLoadingViewPreview() {
    RPAPPTheme {
        RPAppLoadingView()
    }
}

@Composable
fun RPAppLoadingView() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(LOTTIE)
    )
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(CLIP_MIN, CLIP_MAX),
            initialProgress = 0f
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}