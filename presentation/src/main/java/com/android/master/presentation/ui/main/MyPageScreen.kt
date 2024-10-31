package com.android.master.presentation.ui.main

import android.content.Context
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun MyPageScreen(viewModel: ViewModel) {
    val context = LocalContext.current
    val kakaoClient = UserApiClient.instance

    Button(onClick = { kakaoClient.kakaoLogin(context) }) {
        Text("카카오 로그인")
    }
}

private fun UserApiClient.kakaoLogin(context: Context) {
    if (isKakaoTalkLoginAvailable(context)) {
        loginWithKakaoTalk(context) { token, error -> handleKakaoLoginResult(token, error) }
    } else {
        loginWithKakaoAccount(context) { token, error -> handleKakaoLoginResult(token, error) }
    }
}

private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) {
    when {
        error != null && isLoginCancelled(error) -> return
        error != null -> Log.e("KakaoLogin", "카카오계정으로 로그인 실패", error)
        token != null -> Log.i("KakaoLogin", "카카오계정으로 로그인 성공 ${token.accessToken}")
    }
}

private fun isLoginCancelled(error: Throwable): Boolean {
    return error is ClientError && error.reason == ClientErrorCause.Cancelled
}