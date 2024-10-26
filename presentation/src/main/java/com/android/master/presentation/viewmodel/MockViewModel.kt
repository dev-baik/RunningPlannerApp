package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.master.domain.usecase.mock.GetMockDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MockViewModel @Inject constructor(
    private val getMockDataUseCase: GetMockDataUseCase
) : ViewModel()