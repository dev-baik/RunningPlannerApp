package com.android.master.presentation.feature.signin

import com.android.master.presentation.util.base.UiEvent
import com.android.master.presentation.util.base.UiSideEffect
import com.android.master.presentation.util.base.UiState
import com.android.master.presentation.util.view.LoadState

class SignInContract {
    data class SignInUiState(
        val loadState: LoadState = LoadState.Idle,
    ) : UiState

    sealed interface SignInSideEffect : UiSideEffect

    sealed class SignInEvent : UiEvent
}