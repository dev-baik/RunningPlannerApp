package com.android.master.presentation.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.master.domain.model.Profile
import com.android.master.domain.usecase.signin.ClearUserInfoUseCase
import com.android.master.domain.usecase.signin.SetUserProfileUseCase
import com.android.master.presentation.util.view.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val setUserProfileUseCase: SetUserProfileUseCase,
    private val clearUserInfoUseCase: ClearUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInContract.SignInUiState())
    val uiState: StateFlow<SignInContract.SignInUiState>
        get() = _uiState.asStateFlow()
    val currentState: SignInContract.SignInUiState
        get() = uiState.value

    fun setEvent(event: SignInContract.SignInEvent) {
        viewModelScope.launch {
            handleEvent(event)
        }
    }

    private fun handleEvent(event: SignInContract.SignInEvent) {
        when (event) {
            is SignInContract.SignInEvent.SetUserProfile -> {
                _uiState.value = currentState.copy(
                    userProfileLoadState = event.userProfileLoadState,
                    profile = event.profile
                )
            }
            is SignInContract.SignInEvent.OnSuccessLogin -> {
                _uiState.value = currentState.copy(loadState = event.loadState)
            }
        }
    }

    fun setUserProfile(profile: Profile) {
        viewModelScope.launch {
            setEvent(SignInContract.SignInEvent.OnSuccessLogin(loadState = LoadState.Loading))
            setUserProfileUseCase(profile)
            setEvent(
                SignInContract.SignInEvent.SetUserProfile(
                    userProfileLoadState = LoadState.Success,
                    profile = profile
                )
            )
        }
    }

    fun clearUserInfo() {
        viewModelScope.launch {
            clearUserInfoUseCase()
        }
    }
}