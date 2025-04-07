package com.android.master.presentation.feature.signin

import com.android.master.domain.model.Profile
import com.android.master.presentation.util.base.UiEvent
import com.android.master.presentation.util.base.UiSideEffect
import com.android.master.presentation.util.base.UiState
import com.android.master.presentation.util.view.LoadState

class SignInContract {
    data class SignInUiState(
        val loadState: LoadState = LoadState.Idle,
        val userProfileLoadState: LoadState = LoadState.Idle,
        val profile: Profile = Profile()
    ) : UiState

    sealed interface SignInSideEffect : UiSideEffect

    sealed class SignInEvent : UiEvent {
        data class OnSuccessLogin(val loadState: LoadState) : SignInEvent()
        data class SetUserProfile(val userProfileLoadState: LoadState, val profile: Profile) : SignInEvent()
    }
}