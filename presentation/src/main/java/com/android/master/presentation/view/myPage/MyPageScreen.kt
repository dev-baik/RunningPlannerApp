package com.android.master.presentation.view.myPage

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.master.domain.model.AccountInfo
import com.android.master.presentation.R
import com.android.master.presentation.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(
    context: Context,
    viewModel: MainViewModel,
    googleSignInClient: GoogleSignInClient,
    scaffoldState: SnackbarHostState
) {
    val accountInfo by viewModel.accountInfo.collectAsStateWithLifecycle()
    Log.i("AccountInfo", accountInfo.toString())

    val scope = rememberCoroutineScope()

    val googleLoginForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(it)
                signInWithCredential(context, task, viewModel, scope, scaffoldState)
            }
        }
    }

    Column {
        LoginButton("카카오 로그인") {
            UserApiClient.instance.loginKakao(context, viewModel, scope, scaffoldState)
        }
        LoginButton("네이버 로그인") {
            loginNaver(context, viewModel, scope, scaffoldState)
        }
        LoginButton("구글 로그인") {
            googleLoginForResult.launch(googleSignInClient.signInIntent)
        }
    }
}

@Composable
fun LoginButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}

private fun signInWithCredential(
    context: Context,
    accountTask: Task<GoogleSignInAccount>,
    viewModel: MainViewModel,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState
) {
    val account = accountTask.result ?: return
    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

    Firebase.auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveAccountInfo(
                    viewModel = viewModel,
                    task = task,
                    email = account.email.orEmpty(),
                    type = AccountInfo.LoginType.GOOGLE
                )
                popupSnackBar(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    message = getString(context, R.string.login_success_message)
                )
            } else {
                popupSnackBar(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    message = getString(context, R.string.login_failed_message)
                )
                viewModel.signOut()
                Firebase.auth.signOut()
            }
        }
}

fun loginNaver(
    context: Context,
    viewModel: MainViewModel,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState
) {
    val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            fetchNaverUserProfile(context, viewModel, scope, scaffoldState)
        }

        override fun onFailure(httpStatus: Int, message: String) {}
        override fun onError(errorCode: Int, message: String) {}
    }

    NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
}

private fun fetchNaverUserProfile(
    context: Context,
    viewModel: MainViewModel,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState
) {
    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(result: NidProfileResponse) {
            result.profile?.let {
                createUserWithEmailAndPassword(
                    context = context,
                    viewModel = viewModel,
                    email = it.email.orEmpty(),
                    uid = it.id.orEmpty(),
                    scope = scope,
                    scaffoldState = scaffoldState,
                    type = AccountInfo.LoginType.NAVER
                )
            }
        }

        override fun onFailure(httpStatus: Int, message: String) {}
        override fun onError(errorCode: Int, message: String) {}
    })
}

private fun UserApiClient.loginKakao(
    context: Context,
    viewModel: MainViewModel,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState
) {
    if (isKakaoTalkLoginAvailable(context)) {
        loginWithKakaoTalk(context) { token, error ->
            handleKakaoLoginResult(context, viewModel, token, error, scope, scaffoldState)
        }
    } else {
        loginWithKakaoAccount(context) { token, error ->
            handleKakaoLoginResult(context, viewModel, token, error, scope, scaffoldState)
        }
    }
}

private fun handleKakaoLoginResult(
    context: Context,
    viewModel: MainViewModel,
    token: OAuthToken?,
    error: Throwable?,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState
) {
    if (error != null) {
        if (isLoginCancelled(error)) return
        popupSnackBar(
            scope = scope,
            scaffoldState = scaffoldState,
            message = getString(context, R.string.login_failed_message)
        )
        return
    }
    token?.let { fetchKakaoUserProfile(context, viewModel, scope, scaffoldState) }
}

private fun fetchKakaoUserProfile(
    context: Context,
    viewModel: MainViewModel,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState
) {
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            popupSnackBar(
                scope = scope,
                scaffoldState = scaffoldState,
                message = getString(context, R.string.login_failed_message)
            )
        } else {
            user?.let {
                createUserWithEmailAndPassword(
                    context = context,
                    viewModel = viewModel,
                    email = it.kakaoAccount?.email.orEmpty(),
                    uid = it.id.toString(),
                    scope = scope,
                    scaffoldState = scaffoldState,
                    type = AccountInfo.LoginType.KAKAO
                )
            }
        }
    }
}

private fun isLoginCancelled(error: Throwable): Boolean {
    return error is ClientError && error.reason == ClientErrorCause.Cancelled
}

private fun createUserWithEmailAndPassword(
    context: Context,
    viewModel: MainViewModel,
    email: String,
    uid: String,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState,
    type: AccountInfo.LoginType
) {
    Firebase.auth.createUserWithEmailAndPassword(email, uid)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveAccountInfo(
                    viewModel = viewModel,
                    task = task,
                    email = email,
                    type = type
                )
                popupSnackBar(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    message = getString(context, R.string.login_success_message)
                )
            }
        }
        .addOnFailureListener {
            if (it is FirebaseAuthUserCollisionException) {
                signInWithEmailAndPassword(
                    context = context,
                    viewModel = viewModel,
                    email = email,
                    uid = uid,
                    scope = scope,
                    scaffoldState = scaffoldState,
                    type = type
                )
            } else {
                popupSnackBar(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    message = getString(context, R.string.login_failed_message)
                )
                viewModel.signOut()
                Firebase.auth.signOut()
            }
        }
}

private fun signInWithEmailAndPassword(
    context: Context,
    viewModel: MainViewModel,
    email: String,
    uid: String,
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState,
    type: AccountInfo.LoginType
) {
    Firebase.auth.signInWithEmailAndPassword(email, uid)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveAccountInfo(
                    viewModel = viewModel,
                    task = task,
                    email = email,
                    type = type
                )
                popupSnackBar(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    message = getString(context, R.string.login_success_message)
                )
            } else {
                popupSnackBar(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    message = getString(context, R.string.login_failed_message)
                )
                viewModel.signOut()
                Firebase.auth.signOut()
            }
        }
}

private fun saveAccountInfo(
    viewModel: MainViewModel,
    task: Task<AuthResult>,
    email: String,
    type: AccountInfo.LoginType
) {
    viewModel.signIn(
        AccountInfo(
            id = task.result.user?.uid.orEmpty(),
            email = email,
            type = type
        )
    )
}

fun popupSnackBar(
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState,
    message: String,
) {
    scope.launch {
        scaffoldState.showSnackbar(message = message)
    }
}