package com.android.master.presentation.ui.main

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

@Composable
fun MyPageScreen(viewModel: ViewModel) {
    val context = LocalContext.current
    val kakaoClient = UserApiClient.instance

    Column {
        Button(onClick = { kakaoClient.loginKakao(context) }) {
            Text("카카오 로그인")
        }
        Button(onClick = { loginNaver(context) }) {
            Text("네이버 로그인")
        }
    }
}

fun loginNaver(context: Context) {
    val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            Log.i("NaverLogin", NaverIdLoginSDK.getAccessToken().toString())
        }

        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            Log.e("NaverLogin", "errorCode:$errorCode, errorDesc:$errorDescription")
        }

        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
}

private fun UserApiClient.loginKakao(context: Context) {
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