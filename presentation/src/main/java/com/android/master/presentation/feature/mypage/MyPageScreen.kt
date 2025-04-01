package com.android.master.presentation.feature.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.master.presentation.ui.theme.RPAPPTheme

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    RPAPPTheme {
        MyPageScreen(PaddingValues())
    }
}

@Composable
fun MyPageRoute(
    padding: PaddingValues
) {
    MyPageScreen(
        padding = padding
    )
}

@Composable
fun MyPageScreen(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Text(text = "MyPage")
    }
}
