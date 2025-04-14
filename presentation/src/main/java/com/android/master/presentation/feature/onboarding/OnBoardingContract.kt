package com.android.master.presentation.feature.onboarding

import androidx.compose.material3.SnackbarDuration
import com.android.master.presentation.util.base.UiEvent
import com.android.master.presentation.util.base.UiSideEffect
import com.android.master.presentation.util.base.UiState

class OnBoardingContract {
    class OnBoardingUiState : UiState

    sealed interface OnBoardingSideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String, val duration: SnackbarDuration = SnackbarDuration.Short) : OnBoardingSideEffect
        data object NavigationToHome : OnBoardingSideEffect
    }

    sealed class OnBoardingEvent : UiEvent
}