package com.android.master.presentation.feature.signin

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.master.presentation.R
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    RPAPPTheme {
        SignInScreen(
            navigateToHome = {}
        )
    }
}

@Composable
fun SignInRoute(
    navigateToHome: () -> Unit
) {
    SignInScreen(
        navigateToHome = navigateToHome
    )
}

@Composable
fun SignInScreen(
    navigateToHome: () -> Unit
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
                    .clickable(onClick = navigateToHome),
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
                    .clickable(onClick = navigateToHome)
            )
            Image(
                painter = painterResource(id = R.drawable.btn_google_login),
                contentDescription = stringResource(id = R.string.sign_in_google_login_description),
                modifier = Modifier
                    .size(50.dp)
                    .clickable(onClick = navigateToHome)
            )
        }
        Spacer(modifier = Modifier.weight(284f))
    }
}