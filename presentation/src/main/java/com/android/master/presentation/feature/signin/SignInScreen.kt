package com.android.master.presentation.feature.signin

import android.content.Context
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
import com.android.master.presentation.R
import com.android.master.presentation.ui.component.view.RPAppLoadingView
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme
import com.android.master.presentation.util.view.LoadState
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

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

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val callback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, throwable ->
        if (throwable != null) {
            // TODO SnackBar
        } else if (oAuthToken != null) {
            // 카카오 유저 정보 가져오기
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    // TODO SnackBar
                    UserApiClient.instance.logout {}
                } else {
                    user?.let {
                        val email = it.kakaoAccount?.email.orEmpty()
                        val uid = it.id.toString()
                        viewModel.setUserProfile(Profile(email, uid))
                    }
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
                }.addOnFailureListener {
                    // 기존 사용자 파이어베이스 로그인
                    if (it is FirebaseAuthUserCollisionException) {
                        Firebase.auth.signInWithEmailAndPassword(
                            viewModel.currentState.profile.email,
                            viewModel.currentState.profile.uid
                        ).addOnSuccessListener {
                            viewModel.setEvent(
                                SignInContract.SignInEvent.OnSuccessLogin(loadState = LoadState.Success)
                            )
                        }.addOnFailureListener {
                            viewModel.clearUserInfo()
                            UserApiClient.instance.logout {}
                            Firebase.auth.signOut()
                        }
                    } else {
                        // TODO SnackBar
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
                    setLayoutLoginKakaoClickListener(context = context, callback = callback)
                },
                onNaverSignInClicked = {},
                onGoogleSignInClicked = {},
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