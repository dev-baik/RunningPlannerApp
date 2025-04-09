package com.android.master.presentation.feature.onboarding

import com.android.master.presentation.util.base.UiEvent
import com.android.master.presentation.util.base.UiSideEffect
import com.android.master.presentation.util.base.UiState

class OnBoardingContract {
    class OnBoardingUiState : UiState

    sealed interface OnBoardingSideEffect : UiSideEffect {
        data object NavigationToHome : OnBoardingSideEffect
    }

    sealed class OnBoardingEvent : UiEvent
}