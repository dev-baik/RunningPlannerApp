package com.android.master.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.master.presentation.BuildConfig
import com.android.master.presentation.ui.theme.RunningPlannerAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunningPlannerAppTheme {
                MainScreen(googleSignInClient)
            }
        }

        setUpSocialLogin()
    }

    private fun setUpSocialLogin() {
        KakaoSdk.init(this, KAKAO_API_KEY)
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = NAVER_CLIENT_ID,
            clientName = NAVER_CLIENT_NAME,
            clientSecret = NAVER_CLIENT_SECRET
        )
    }

    companion object {
        const val KAKAO_API_KEY = BuildConfig.KAKAO_API_KEY
        const val NAVER_CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        const val NAVER_CLIENT_NAME = BuildConfig.NAVER_CLIENT_NAME
        const val NAVER_CLIENT_SECRET = BuildConfig.NAVER_CLIENT_SECRET
    }
}