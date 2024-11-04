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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.android.master.domain.model.AccountInfo
import com.android.master.presentation.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

@Composable
fun MyPageScreen(
    viewModel: MainViewModel,
    googleSignInClient: GoogleSignInClient
) {
    val accountInfo by viewModel.accountInfo.collectAsState()
    Log.i("AccountInfo", accountInfo.toString())

    val context = LocalContext.current
    val kakaoClient = UserApiClient.instance
    val googleLoginForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(it)
                handleGoogleLoginResult(task, viewModel)
            }
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

private fun handleGoogleLoginResult(
    accountTask: Task<GoogleSignInAccount>,
    viewModel: MainViewModel
) {
    val account = accountTask.result ?: return
    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
    Firebase.auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModel.signIn(
                    AccountInfo(
                        accountId = task.result.user?.uid,
                        email = account.email,
                        type = AccountInfo.LoginType.GOOGLE
                    )
                )
            } else {
                viewModel.signOut()
                Firebase.auth.signOut()
            }
        }
}

fun loginNaver(context: Context) {
    val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            fetchNaverUserProfile()
        }

        override fun onFailure(httpStatus: Int, message: String) {
            logNaverError("NaverLogin")
        }

        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
}

private fun fetchNaverUserProfile() {
    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(result: NidProfileResponse) {
            Log.i("NaverProfile", result.toString())
        }

        override fun onFailure(httpStatus: Int, message: String) {
            logNaverError("NaverProfile")
        }

        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    })
}

private fun UserApiClient.loginKakao(context: Context) {
    if (isKakaoTalkLoginAvailable(context)) {
        loginWithKakaoTalk(context) { token, error -> handleKakaoLoginResult(token, error) }
    } else {
        loginWithKakaoAccount(context) { token, error -> handleKakaoLoginResult(token, error) }
    }
}

private fun logNaverError(tag: String) {
    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
    Log.e(tag, "errorCode:$errorCode, errorDesc:$errorDescription")
}

private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) {
    if (error != null) {
        if (isLoginCancelled(error)) return
        Log.e("KakaoLogin", "카카오계정으로 로그인 실패", error)
        return
    }
    token?.let { fetchKakaoUserProfile() }
}

private fun fetchKakaoUserProfile() {
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