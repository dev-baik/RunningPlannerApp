package com.android.master.domain.model

data class AccountInfo(
    val id: String,
    val email: String,
    val type: LoginType? = null
) {
    enum class LoginType {
        KAKAO, GOOGLE, NAVER
    }
}