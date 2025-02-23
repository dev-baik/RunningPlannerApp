package com.android.master.presentation.state

import com.android.master.domain.model.AccountInfo

sealed class MyPageState {
    object Idle : MyPageState()
    object Loading : MyPageState()
    data class Success(val accountInfo: AccountInfo) : MyPageState()
    data class Error(val message: String) : MyPageState()
}