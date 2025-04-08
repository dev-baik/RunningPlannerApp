package com.android.master.presentation.feature.navigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.android.master.presentation.BuildConfig.KAKAO_NATIVE_API_KEY
import com.android.master.presentation.BuildConfig.NAVER_CLIENT_ID
import com.android.master.presentation.BuildConfig.NAVER_CLIENT_NAME
import com.android.master.presentation.BuildConfig.NAVER_CLIENT_SECRET
import com.android.master.presentation.feature.splash.SplashScreen
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setKakao()
        setNaver()
        enableEdgeToEdge()
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            RPAPPTheme {
                LaunchedEffect(Unit) {
                    delay(SPLASH_SCREEN_DELAY)
                    showSplash = false
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    MainScreen()
                }
            }
        }
    }

    private fun setKakao() {
        KakaoSdk.init(this, KAKAO_NATIVE_API_KEY)
    }

    private fun setNaver() {
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = NAVER_CLIENT_ID,
            clientName = NAVER_CLIENT_NAME,
            clientSecret = NAVER_CLIENT_SECRET
        )
    }

    companion object {
        const val SPLASH_SCREEN_DELAY = 2000L
    }
}