package com.android.master.presentation.feature.signin

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.master.domain.model.Profile
import com.android.master.domain.model.Profile.Platform.GOOGLE
import com.android.master.domain.model.Profile.Platform.KAKAO
import com.android.master.domain.model.Profile.Platform.NAVER
import com.android.master.presentation.BuildConfig.GOOGLE_CLIENT_ID
import com.android.master.presentation.R
import com.android.master.presentation.ui.component.view.RPAppLoadingView
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme
import com.android.master.presentation.util.view.LoadState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    RPAPPTheme {
        SignInScreen(
            onKakaoSignInClicked = {},
            onNaverSignInClicked = {},
            onGoogleSignInClicked = {}
        )
    }
}

fun setLayoutLoginKakaoClickListener(
    context: Context,
    callback: (OAuthToken?, Throwable?) -> Unit
) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}

fun setLayoutLoginNaverClickListener(
    context: Context,
    callback: OAuthLoginCallback
) {
    NaverIdLoginSDK.authenticate(context, callback)
}

private fun getGoogleClient(context: Context): GoogleSignInClient {
    val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(GOOGLE_CLIENT_ID)
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, googleSignInOption)
}

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit,
    onShowSnackbar: (String, SnackbarDuration) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val oauthKakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, throwable ->
        if (throwable != null) {
            onShowSnackbar(throwable.message.toString(), SnackbarDuration.Short)
        } else if (oAuthToken != null) {
            // 카카오 유저 정보 가져오기
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    onShowSnackbar(error.message.toString(), SnackbarDuration.Short)
                    UserApiClient.instance.logout {}
                } else {
                    user?.let {
                        viewModel.setUserProfile(
                            Profile(
                                email = it.kakaoAccount?.email.orEmpty(),
                                uid = it.id.toString(),
                                platform = KAKAO
                            )
                        )
                    }
                }
            }
        }
    }

    val oauthNaverLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            // 네이버 유저 정보 가져오기
            NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                override fun onSuccess(result: NidProfileResponse) {
                    result.profile?.let {
                        viewModel.setUserProfile(
                            Profile(
                                email = it.email.orEmpty(),
                                uid = it.id.toString(),
                                platform = NAVER
                            )
                        )
                    }
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    onShowSnackbar(message, SnackbarDuration.Short)
                }

                override fun onError(errorCode: Int, message: String) {
                    onShowSnackbar(message, SnackbarDuration.Short)
                }
            })
        }

        override fun onFailure(httpStatus: Int, message: String) {
            onShowSnackbar(message, SnackbarDuration.Short)
        }

        override fun onError(errorCode: Int, message: String) {
            onShowSnackbar(message, SnackbarDuration.Short)
        }
    }

    val googleLoginForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                Firebase.auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        viewModel.loginGoogleUser(
                            profile = Profile(
                                email = account.email.orEmpty(),
                                uid = it.user?.uid.orEmpty(),
                                platform = GOOGLE
                            ),
                            isNewUser = it.additionalUserInfo?.isNewUser ?: true
                        )
                    }
                    .addOnFailureListener {
                        onShowSnackbar(it.message.toString(), SnackbarDuration.Short)
                        viewModel.clearUserInfo()
                        viewModel.setEvent(
                            SignInContract.SignInEvent.SetUserProfile(
                                userProfileLoadState = LoadState.Error,
                                profile = Profile()
                            )
                        )
                    }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (tokenInfo != null) {
                    viewModel.setEvent(
                        SignInContract.SignInEvent.OnSuccessLogin(loadState = LoadState.Success)
                    )
                } else if (error != null) {
                    onShowSnackbar(error.message.toString(), SnackbarDuration.Short)
                }
            }
        }
    }

    LaunchedEffect(uiState.userProfileLoadState) {
        when (uiState.userProfileLoadState) {
            LoadState.Success -> {
                // 파이어베이스 회원가입
                Firebase.auth.createUserWithEmailAndPassword(
                    viewModel.currentState.profile.email,
                    viewModel.currentState.profile.uid
                ).addOnSuccessListener {
                    viewModel.setEvent(
                        SignInContract.SignInEvent.OnSuccessLogin(loadState = LoadState.Error)
                    )
                }.addOnFailureListener { outerException ->
                    // 기존 사용자 파이어베이스 로그인
                    if (outerException is FirebaseAuthUserCollisionException) {
                        Firebase.auth.signInWithEmailAndPassword(
                            viewModel.currentState.profile.email,
                            viewModel.currentState.profile.uid
                        ).addOnSuccessListener {
                            viewModel.setEvent(
                                SignInContract.SignInEvent.OnSuccessLogin(loadState = LoadState.Success)
                            )
                        }.addOnFailureListener { innerException ->
                            onShowSnackbar(innerException.message.toString(), SnackbarDuration.Short)
                            viewModel.clearUserInfo()
                            UserApiClient.instance.logout {}
                            Firebase.auth.signOut()
                        }
                    } else {
                        onShowSnackbar(outerException.message.toString(), SnackbarDuration.Short)
                        viewModel.clearUserInfo()
                        UserApiClient.instance.logout {}
                        Firebase.auth.signOut()
                    }
                }
            }

            else -> Unit
        }
    }

    when (uiState.loadState) {
        LoadState.Idle -> {
            SignInScreen(
                onKakaoSignInClicked = {
                    setLayoutLoginKakaoClickListener(
                        context = context,
                        callback = oauthKakaoLoginCallback
                    )
                },
                onNaverSignInClicked = {
                    setLayoutLoginNaverClickListener(
                        context = context,
                        callback = oauthNaverLoginCallback
                    )
                },
                onGoogleSignInClicked = {
                    googleLoginForResult.launch(getGoogleClient(context).signInIntent)
                }
            )
        }

        LoadState.Loading -> RPAppLoadingView()

        // 기존 사용자
        LoadState.Success -> navigateToHome()

        // 새로운 사용자
        LoadState.Error -> navigateToOnboarding()
    }
}

@Composable
fun SignInScreen(
    onKakaoSignInClicked: () -> Unit,
    onNaverSignInClicked: () -> Unit,
    onGoogleSignInClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RPAppTheme.colors.green),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(226f))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.splash_logo_description),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        )
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(RPAppTheme.colors.kakaoYello)
                    .clickable(onClick = onKakaoSignInClicked),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_kakao_logo),
                    contentDescription = stringResource(id = R.string.sign_in_kakao_login)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.btn_naver_login),
                contentDescription = stringResource(id = R.string.sign_in_naver_login_description),
                modifier = Modifier
                    .size(50.dp)
                    .clickable(onClick = onNaverSignInClicked)
            )
            Image(
                painter = painterResource(id = R.drawable.btn_google_login),
                contentDescription = stringResource(id = R.string.sign_in_google_login_description),
                modifier = Modifier
                    .size(50.dp)
                    .clickable(onClick = onGoogleSignInClicked)
            )
        }
        Spacer(modifier = Modifier.weight(284f))
    }
}