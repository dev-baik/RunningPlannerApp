package com.android.master.presentation.feature.video

import androidx.compose.material3.SnackbarDuration
import com.android.master.presentation.util.base.UiEvent
import com.android.master.presentation.util.base.UiSideEffect
import com.android.master.presentation.util.base.UiState
import com.android.master.presentation.util.view.LoadState

class VideoContract {
    data class VideoUiState(
        val loadState: LoadState = LoadState.Idle,
        val videoLoadState: LoadState = LoadState.Idle,
        val keyword: String = "",
        val isVideoDialogOpen: Boolean = false,
        val url: String = "",
        val height: Int = 0
    ) : UiState

    sealed interface VideoSideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String, val duration: SnackbarDuration = SnackbarDuration.Short) : VideoSideEffect
    }

    sealed class VideoEvent : UiEvent {
        data object DismissDialogVideo : VideoEvent()
        data class OnDialogVideo(val url: String, val height: Int) : VideoEvent()
        data class OnKeywordValueChange(val keyword: String) : VideoEvent()
        data class FetchVideos(val videoLoadState: LoadState) : VideoEvent()
    }
}