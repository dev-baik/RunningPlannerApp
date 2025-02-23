package com.android.master.presentation.view.myPage

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.master.domain.model.AccountInfo
import com.android.master.presentation.BuildConfig
import com.android.master.presentation.R
import com.android.master.presentation.intent.MyPageIntent
import com.android.master.presentation.state.MyPageState
import com.android.master.presentation.viewmodel.MyPageViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
    scaffoldState: SnackbarHostState
) {
    val context = LocalContext.current

    val viewModel: MyPageViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyPageEffect.ShowSnackbar -> scaffoldState.showSnackbar(effect.message)
            }
        }
    }

    val googleLoginForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                signInWithCredential(task, viewModel)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            is MyPageState.Idle, is MyPageState.Error -> {
                if (state is MyPageState.Error) {
                    Text(
                        text = (state as MyPageState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                LoginButton(text = "카카오 로그인") {
                    UserApiClient.instance.loginKakao(context, viewModel, scope, scaffoldState)
                }
                Spacer(modifier = Modifier.height(16.dp))
                LoginButton(text = "네이버 로그인") {
                    loginNaver(context, viewModel)
                }
                Spacer(modifier = Modifier.height(16.dp))
                LoginButton(text = "구글 로그인") {
                    googleLoginForResult.launch(getGoogleClient(context).signInIntent)
                }
            }

            is MyPageState.Loading -> {
                CircularProgressIndicator()
            }

            is MyPageState.Success -> {
                val accountInfo = (state as MyPageState.Success).accountInfo
                Text(text = "$accountInfo")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.sendIntent(MyPageIntent.Logout) }) {
                    Text(text = "로그아웃")
                }
            }
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
    accountTask: Task<GoogleSignInAccount>,
    viewModel: MyPageViewModel,
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
            } else {
                viewModel.sendIntent(MyPageIntent.Logout)
                Firebase.auth.signOut()
            }
        }
}

fun loginNaver(
    context: Context,
    viewModel: MyPageViewModel,
) {
    val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            fetchNaverUserProfile(viewModel)
        }

        override fun onFailure(httpStatus: Int, message: String) {}
        override fun onError(errorCode: Int, message: String) {}
    }

    NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
}

private fun fetchNaverUserProfile(
    viewModel: MyPageViewModel
) {
    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(result: NidProfileResponse) {
            result.profile?.let {
                createUserWithEmailAndPassword(
                    viewModel = viewModel,
                    email = it.email.orEmpty(),
                    uid = it.id.orEmpty(),
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
    viewModel: MyPageViewModel,
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
    viewModel: MyPageViewModel,
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
    viewModel: MyPageViewModel,
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
                    viewModel = viewModel,
                    email = it.kakaoAccount?.email.orEmpty(),
                    uid = it.id.toString(),
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
    viewModel: MyPageViewModel,
    email: String,
    uid: String,
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
            }
        }
        .addOnFailureListener {
            if (it is FirebaseAuthUserCollisionException) {
                signInWithEmailAndPassword(
                    viewModel = viewModel,
                    email = email,
                    uid = uid,
                    type = type
                )
            } else {
                viewModel.sendIntent(MyPageIntent.Logout)
                Firebase.auth.signOut()
            }
        }
}

private fun signInWithEmailAndPassword(
    viewModel: MyPageViewModel,
    email: String,
    uid: String,
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
            } else {
                viewModel.sendIntent(MyPageIntent.Logout)
                Firebase.auth.signOut()
            }
        }
}

private fun saveAccountInfo(
    viewModel: MyPageViewModel,
    task: Task<AuthResult>,
    email: String,
    type: AccountInfo.LoginType
) {
    viewModel.sendIntent(
        MyPageIntent.SignIn(
            AccountInfo(
                id = task.result.user?.uid.orEmpty(),
                email = email,
                type = type
            )
        )
    )
}

fun popupSnackBar(
    scope: CoroutineScope,
    scaffoldState: SnackbarHostState,
    message: String,
) {
    scope.launch {
        scaffoldState.showSnackbar(message)
    }
}

private fun getGoogleClient(context: Context): GoogleSignInClient {
    val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, googleSignInOption)
}