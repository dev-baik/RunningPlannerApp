package com.android.master.presentation.ui.main

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.android.master.presentation.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

@Composable
fun MyPageScreen(
    viewModel: MainViewModel,
    googleSignInClient: GoogleSignInClient
) {
    val context = LocalContext.current
    val kakaoClient = UserApiClient.instance
    val googleLoginForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)

            val account = task.result
            Log.i("GoogleLogin", account.idToken.toString())
        } else {
            Log.e("GoogleLogin", result.toString())
        }
    }

    Column {
        Button(onClick = { kakaoClient.loginKakao(context) }) {
            Text("카카오 로그인")
        }
        Button(onClick = { loginNaver(context) }) {
            Text("네이버 로그인")
        }
        Button(onClick = { googleLoginForResult.launch(googleSignInClient.signInIntent) }) {
            Text("구글 로그인")
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
    if (error != null) {
        if (isLoginCancelled(error)) return
        Log.e("KakaoLogin", "카카오계정으로 로그인 실패", error)
        return
    }
    token?.let { fetchUserProfile() }
}

private fun fetchUserProfile() {
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Log.e("KakaoProfile", "사용자 정보 가져오기 실패", error)
        } else {
            user?.let {
                Log.i("KakaoPrifle", "${it.id} ${it.kakaoAccount?.email}")
            }
        }
    }
}

private fun isLoginCancelled(error: Throwable): Boolean {
    return error is ClientError && error.reason == ClientErrorCause.Cancelled
}