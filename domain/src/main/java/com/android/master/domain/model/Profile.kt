package com.android.master.domain.model

data class Profile(
    val email: String = "",
    val uid: String = "",
    val platform: Platform = Platform.NONE
) {
    enum class Platform {
        NONE,
        KAKAO,
        NAVER,
        GOOGLE
    }
}