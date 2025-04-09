package com.android.master.presentation.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel : ViewModel() {

    private val _sideEffect: MutableSharedFlow<OnBoardingContract.OnBoardingSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<OnBoardingContract.OnBoardingSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun setSideEffect(sideEffect: OnBoardingContract.OnBoardingSideEffect) {
        viewModelScope.launch { _sideEffect.emit(sideEffect) }
    }
}