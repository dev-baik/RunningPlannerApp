package com.android.master.presentation.view.myPage

sealed class MyPageEffect {
    data class ShowSnackbar(val message: String) : MyPageEffect()
}