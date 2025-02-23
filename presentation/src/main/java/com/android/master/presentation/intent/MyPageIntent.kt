package com.android.master.presentation.intent

import com.android.master.domain.model.AccountInfo

sealed class MyPageIntent {
    data class SignIn(val accountInfo: AccountInfo) : MyPageIntent()
    object Logout : MyPageIntent()
}