package com.android.master.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.master.presentation.BuildConfig
import com.android.master.presentation.ui.theme.RunningPlannerAppTheme
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunningPlannerAppTheme {
                MainScreen()
            }
        }

        KakaoSdk.init(this, KAKAO_API_KEY)
    }

    companion object {
        const val KAKAO_API_KEY = BuildConfig.KAKAO_API_KEY
    }
}