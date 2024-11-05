package com.android.master.domain.model

data class AccountInfo(
    val id: String? = null,
    val email: String? = null,
    val type: LoginType? = null
) {
    enum class LoginType {
        KAKAO, GOOGLE, NAVER
    }
}