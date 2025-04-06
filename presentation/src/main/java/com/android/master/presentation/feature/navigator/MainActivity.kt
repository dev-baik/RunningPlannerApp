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
import com.android.master.presentation.BuildConfig
import com.android.master.presentation.feature.splash.SplashScreen
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setKakao()
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
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_API_KEY)
    }

    companion object {
        const val SPLASH_SCREEN_DELAY = 2000L
    }
}